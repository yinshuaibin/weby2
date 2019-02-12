package com.ferret.service.bukong;

import java.util.List;

import com.ferret.bean.BuKongGroup;
import com.ferret.bean.InterfaceBean.BuKongContorlType;
import com.ferret.bean.User;
import com.ferret.exception.BusinessException;

/**
 * 布控分组业务层
 * @author zs
 *
 */
public interface BuKongGroupService {

    /**
     * 分页查询所有布控分组   y 1225
     * @return
     */
    public List<BuKongContorlType> findAllBuKongGroup(int pageNum, int pageSize);

    /** 查询所有布控分组总数 */
    int findAllBuKongGroupCount();

    /** 查询所有布控分组 */
    List<BuKongContorlType> findAllBuKongGroup();

    /**
     * 校验分组名称  分组名称不能重复   y 1226
     * @param name
     * @return
     */
    public String checkBuKongGroupName(String name);

    /**
     * 添加布控分组  y 1226
     * @param buKongGroup
     * @return
     */
    public String addBuKongGroup(BuKongContorlType buKongGroup);


    /**
     * 根据布控分组id删除该布控分组(如果该分组下有布控数据,一同删除)  y 1226
     * @param buKongGroupId
     * @return
     */
    public String delBuKongGroup(String buKongGroupId);

    /**
     * 修改布控分组信息   y 1226
     * @param buKongGroup
     * @return
     */
    public String updateBuKongGroup(BuKongContorlType buKongGroup);

    /** 根据布控分组id 修改布控分组状态（分组是否布控） */
    String updateBuKongIscontrol(int isControl, int id);
}
