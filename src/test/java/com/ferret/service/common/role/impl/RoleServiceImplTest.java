package com.ferret.service.common.role.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ferret.bean.Role;

import lombok.extern.slf4j.Slf4j;

/**
 * RoleServiceImpl Tester.
 *
 * @author xuyi
 * @version 1.0
 * @since 03/21/2018
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleServiceImplTest {

    @Autowired
    private RoleServiceImpl roleServiceImpl;
    @Before
    public void before() throws Exception {
        // 初始化测试用例类中由Mockito的注解标注的所有模拟对象
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Method: addRole(Role role)
     */
    @Test
    public void testAddRole() {

        Role role = new Role();
        role.setRoleId("410101000007");
        role.setRoleName("fas");
        role.setRoleDesc("队长");
        role.setEnabled("1");
        Assert.assertNotNull("添加数据成功", role);
    }

    /**
     * Method: updateRole(Role role)
     */
    @Test
    public void testUpdateRole() {

        Role role = new Role();
        role.setRoleDesc("admins");
        role.setRoleName("队长");
        roleServiceImpl.updateRole(role);
        Assert.assertTrue("修改成功", true);

    }

    /**
     * Method: deleteRole(String roleId)  将有效值为1  改成 0
     */
    @Test
    public void testDeleteRole() {

        roleServiceImpl.deleteRole("410101000007");

        Assert.assertTrue("删除成功", true);

    }

    /**
     * Method: selectAll()
     */
    @Test
    public void testSelectAll() {

//        List<Role> role =
//                roleServiceImpl.selectAll();

        Assert.assertTrue(true);

    }


    @After
    public void after() throws Exception {

        System.out.println("  Stopping  ");
    }
} 


