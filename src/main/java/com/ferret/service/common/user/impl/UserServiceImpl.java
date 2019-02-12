package com.ferret.service.common.user.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ferret.bean.User;
import com.ferret.exception.BusinessException;
import com.ferret.exception.ServiceException;
import com.ferret.dao.SequenceMapper;
import com.ferret.dao.UserCameraMapper;
import com.ferret.dao.UserMapper;
import com.ferret.service.common.user.UserService;
import com.github.pagehelper.PageHelper;

/**
 * @pack: com.ferret.service.common.user.impl;
 * @auth: Administrator;
 * @since: 2017/12/27 0027;
 * @desc:
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private SequenceMapper sequenceMapper;

	@Autowired
	private UserCameraMapper userCameraMapper;

	private  final String ALGORITH_NAME = "md5";

	/**
	 * 根据用户名查询
	 * 
	 * @param username
	 * @return
	 */
	@Override
	public User findByUsername(String username) {

		return userMapper.findByUsername(username);
	}

	/**
	 * 增加用户数据信息
	 * 
	 * @param user
	 */
	@Override
	public User saveUser(User user) {
		Assert.notNull(user, "新增数据时,不能为空");
		String userID = sequenceMapper.nextVal("user_id");
		user.setUserId(userID);
		int count = userCameraMapper.insertUserCamera(userID, user.getAreaId());
        String salt = UUID.randomUUID().toString().replace("-", "").substring(0,10);
        user.setSalt(salt);
        user.setPassword(new SimpleHash(ALGORITH_NAME,user.getPassword(),user.getCredentialsSalt()).toString());
		int userType = userMapper.insertUser(user);
		if (userType < 1 || count < 1) {
			throw new ServiceException("增加用户数据失败");
		}
		return user;
	}

	/**
	 * 修改用户信息数据
	 * 
	 * @param user
	 */
	@Override
	public void updateUser(User user) {
		Assert.notNull(user, "修改用户信息不能为空");
		userCameraMapper.deleteByUserId(user.getUserId());
		userCameraMapper.insertUserCamera(user.getUserId(), user.getAreaId());
		String salt = UUID.randomUUID().toString().replace("-", "").substring(0,10);
		user.setSalt(salt);
		user.setPassword(new SimpleHash(ALGORITH_NAME,user.getPassword(),user.getCredentialsSalt()).toString());
		int userTypes = userMapper.updateUser(user);
		if (userTypes < 1) {
			throw new BusinessException("修改用户数据失败");
		}

	}

	/**
	 * 删除用户信息数据
	 * 
	 * @param userId
	 */
	@Override
	public void deleteUser(String userId) {
		//删除user表中的数据
		userMapper.deleteUser(userId);
		//删除usercamera表中的数据
		userCameraMapper.deleteByUserId(userId);
	}

	/**
	 * 查询总条数
	 * 修改:不再从后台获取用户对应的areaid,改为从前台获取   y 0814
	 * @return
	 */
	@Override
	public int findCount(String areaId) {
		int count = 0;
		// 根据地区id查询所有符合条件的用户条数
		if (StringUtils.equals("1", areaId)) {
			count = userMapper.findCount() - 1;
		} else {
			count = userMapper.findCountByArea(areaId);
		}
		if (count < 1) {
			throw new BusinessException("获取失败");
		}
		return count;
	}

	@Override
	public List<User> findByArea(Integer pageNo, Integer pageSize, String areaId) throws BusinessException {
		PageHelper.startPage(pageNo, pageSize);
		List<User> userList = null;
		if (StringUtils.equals("1", areaId)) {
			userList = userMapper.findAllUser();
		} else {
			userList = userMapper.findByArea(areaId);
		}
		for (User s:userList) {
			if (s.getOverTime() != null) {
				s.setOverTime(s.getOverTime().substring(0, 10));
			}
		}
		return userList;
	}

	@Override
	public User findUserByIdCard(String idCard) {
        User userByIdCard = userMapper.findUserByIdCard(idCard);
        if(userByIdCard != null){
			if(null != userByIdCard.getCreateTime()) {
				userByIdCard.setCreateTime(userByIdCard.getCreateTime().substring(0, 19));
			}
			if(null != userByIdCard.getOverTime()) {
				userByIdCard.setOverTime(userByIdCard.getOverTime().substring(0, 19));
			}
            userByIdCard.setPassword(null);
        }
        return userByIdCard;
	}

    /**
     * 根据用户名和身份证号校验该身份证号是否重复
     * @param idCard
     * @param userId
     * @return
     */
    @Override
    public User checkUserByIdcardAndUserId(String idCard,String userId) {
        return userMapper.checkUserByIdcardAndUserId(idCard,userId);
    }

}
