package com.ferret.service.common.city.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * CityServiceImpl Tester.
 *
 * @author cc
 * @since 03/21/2018
 * @version 1.0
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
// @ActiveProfiles("test")
public class CityServiceImplTest {
    @Autowired
    private CityServiceImpl cityServiceImpl;

    @Before
    public void before() throws Exception {
        System.out.println("this is Running");
    }

    /**
     *
     * Method: findAllObject()
     *
     */
    @Test
    public void testFindAllObject() {

        List<Map<String,Object>> list= cityServiceImpl.findAllObject();

        Assert.assertTrue(true);

    }

    /**
     *
     * Method: findByCode(Integer code)  采用模糊查询
     *
     */
    @Test
    public void testFindByCode() {

        List<Map<String,Object>> mapList=
                cityServiceImpl.findByCode(4114);

       Assert.assertEquals("商丘市",4114,4114);
    }

    @After
    public void after() throws Exception {

        System.out.println("this is stopping");
    }


}



