package com.ferret.service.common.role.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferret.bean.Authc;
import com.ferret.bean.Role;
import com.ferret.bean.User;
import com.ferret.common.base.Common;
import com.ferret.dao.AuthcMapper;
import com.ferret.dao.RoleAuthcMapper;
import com.ferret.dao.RoleMapper;
import com.ferret.dao.SequenceMapper;
import com.ferret.dao.UserCameraMapper;
import com.ferret.dao.UserMapper;
import com.ferret.service.common.role.RoleService;

/**
 * 修改人:y 修改时间:0724 修改原因:因页面变动,修改后台逻辑 从树-->列表形式,后台手动添加父节点
 * 
 * @author bin
 *
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService, Common {

	@Resource
	private RoleMapper roleMapper;
	@Resource
	private RoleAuthcMapper roleAuthcMapper;
	@Resource
	private UserCameraMapper userCameraMapper;
	@Resource
	private SequenceMapper seqMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AuthcMapper authcMapper;

	@Override
	public boolean addRole(Role role) {
		role.setRoleId(seqMapper.nextVal("role_id"));
		// 角色信息入库
		int roleCount = roleMapper.insertOne(role);
		// 如入库条数小于1, 说明插入失败
		if (roleCount < 1) {
			return false;
		}
		// 角色id,权限id 入库
		int authcCount = roleAuthcMapper.insertAuthorityIds(role.getAuthorityIds(),role.getRoleId());
		// 如入库条数小于1, 说明插入失败
		if (authcCount < 1) {
			return false;
		}
		return true;
	}

	/**
	 * 修改人:y
	 * 修改原因:因插入角色权限规则改变,修改权限的方法也改变了
	 * 修改日期:0724
	 */
	@Override
	public boolean updateRole(Role role) {
		// 修改角色信息
		int count = roleMapper.updateById(role);
		// 修改对应的权限
		roleAuthcMapper.deleteByRoleId(role.getRoleId());
		roleAuthcMapper.insertAuthorityIds(role.getAuthorityIds(),role.getRoleId());
		// 更改roleTreeMap中对应权限的数据
		// roleTreeMap.put(role.getRoleId(), RoleUtils.getChiled(role.getAuthcs()));
		return count >= 1;
	}

	/**
	 * 修改人:y 修改时间:0724 修改原因:删除roleTreeMap
	 */
	@Override
	public boolean deleteRole(String roleId) {
		// 删除时首先查询是否有角色拥有此角色,如果有,则不让其删除
		List<User> users = userMapper.findUserByRoleId(roleId);
		if (users != null && users.size() > 0) {
			return false;
		}
		// 删除role
		int count = roleMapper.deleteById(roleId);
		// 删除role对应的authc
		int count2 = roleAuthcMapper.deleteByRoleId(roleId);
		if (count < 1 || count2 < 1) {
			return false;
		}
		return true;
	}

	@Override
	public List<Role> getRoleByPage(Integer pageNo, Integer pageSize) {
		Integer startNum = (pageNo - 1) * pageSize;
		return roleMapper.getRoleByPage(startNum, pageSize);
	}

	@Override
	public int selectCount() {
		return roleMapper.selectAllCount();
	}

	@Override
	public Role selectByRoleName(String roleName) {
		// 通过roleName查询role
		return roleMapper.selectRoleByRoleName(roleName);
	}

	@Override
	public List<Role> selectAllRole() {
		return roleMapper.selectAll();
	}

	/**
	 * 根据roleId查询对应的authc里页面跳转路径的名称
	 */
	@Override
	public List<String> getAuthcMenuName(String roleId) {
		return authcMapper.selectMenuNameByRoleId(roleId);
	}

	/**
	 * 根绝roleId查询对应的权限
	 * 修改人:y
	 * 修改原因:因页面变动,更改此逻辑
	 * @param roleId
	 * @return
	 */
	@Override
	public List<Authc> getAuthcsByRoleId(String roleId) {
		//查询出所有对应的权限数据
		Role role = roleMapper.selectByRoleId(roleId);
		List<Authc> authcs = role.getAuthcs();
		Set<String> parentSet = new HashSet<>();
		//删除所有的父节点数据,只保留子节点数据
		for (Authc a : authcs) {
			if (StringUtils.isNotBlank(a.getParentId())) {
				parentSet.add(a.getParentId());
			}
		}
		for (String authorityId : parentSet) {
			for (int x = 0; x < authcs.size(); x++) {
				if (authcs.get(x).getAuthorityId().equals(authorityId)) {
					authcs.remove(x);
					x--;
				}
			}
		}
		return authcs;
	}
}
