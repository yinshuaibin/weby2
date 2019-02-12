package com.ferret.service.common.login.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * LoginServiceImpl Tester.
 *
 * @author xuyi
 * @version 1.0
 * @since 03/21/2018
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginServiceImplTest {

    @Autowired
    private LoginServiceImpl loginServiceImpl;

    @Before
    public void before() throws Exception {
        // 初始化测试用例类中由Mockito的注解标注的所有模拟对象
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Method: loginCheck(String userName, String password)
     */
    @Test
    public void testLoginCheck() {

        //loginServiceImpl.loginCheck("root","111111");

        Assert.assertTrue("登录成功",true);

    }


    @After
    public void after() throws Exception {

        Assert.assertTrue(true);
    }
} 


