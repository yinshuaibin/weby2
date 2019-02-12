package com.ferret.service.cameraInfoservice.impl;

import com.ferret.bean.CameraInfo;
import com.ferret.service.common.SequenceService;
import com.ferret.dao.CameraInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * CameraInfoServiceImpl Tester.
 *
 * @author xuyi
 * @version 1.0
 * @since 03/21/2018
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class CameraInfoServiceImplTest {
    @Autowired
    private CameraInfoMapper cameraInfoMapper;
    @Autowired
    private SequenceService sequenceService;

    @Before
    public void before() throws Exception {
        System.out.println("   Running ");
    }

    /**
     * Method: saveCameraInfo(CameraInfo cameraInfo)
     */
    @Test
    public void testSaveCameraInfo() {

        CameraInfo cameraInfo=new CameraInfo();
        Assert.assertNotNull("添加数据时不能为空",cameraInfo);
        String number = sequenceService.createCameraId(cameraInfo.getGroupId());
         cameraInfo.setId(0);
         cameraInfo.setNumber(number);
         cameraInfo.setGroupId("4101010003");
         cameraInfo.setName("测试3");
         cameraInfo.setIp("98.137.142.4");
         cameraInfo.setUsername("admin");
         cameraInfo.setPassword("dsfs");
         cameraInfo.setDevicePort(8080);
         cameraInfo.setAddTime(new Date());
         cameraInfo.setEnabled(true);
        System.out.println("添加成功");

    }

    /**
     * Method: updateCameraInfo(CameraInfo cameraInfo)
     */
    @Test
    public void testUpdateCameraInfo() {

      CameraInfo cameraInfo=cameraInfoMapper.findById(2);
      Assert.assertNotNull("修改数据不能为空",cameraInfo);
      cameraInfo.setName("胡达");
      System.out.println("修改成功");
    }

    /**
     * Method: deleteCameraInfo(Integer id)
     */
    @Test
    public void testDeleteCameraInfo() {
//TODO: Test goes here... 
    }

    /**
     * Method: findByID(Integer id)
     */
    @Test
    public void testFindByID() {
//TODO: Test goes here... 
    }

    /**
     * Method: findAllCameraInfo(Integer pageNo, Integer pageSize)
     */
    @Test
    public void testFindAllCameraInfo() {
//TODO: Test goes here... 
    }

    /**
     * Method: findCount()
     */
    @Test
    public void testFindCount() {
//TODO: Test goes here... 
    }

    /**
     * Method: findByCameraInfoByNumber(String group_id)
     */
    @Test
    public void testFindByCameraInfoByNumber() {
//TODO: Test goes here... 
    }

    /**
     * Method: number2Id(String numbers, String like)
     */
    @Test
    public void testNumber2Id() {
//TODO: Test goes here... 
    }

    /**
     * Method: listCamerasByRoleId(Integer pageNo, Integer pageSize, String roleId)
     */
    @Test
    public void testListCamerasByRoleId() {
//TODO: Test goes here... 
    }


    @After
    public void after() throws Exception {

        System.out.println("   Stopping  ");
    }
} 


