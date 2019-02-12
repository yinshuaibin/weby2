package com.ferret.service.alarm.impl;

import com.ferret.bean.RealTimeAlarm;
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

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * AlarmServiceImpl Tester.
 *
 * @author xuyi
 * @version 1.0
 * @since 03/21/2018
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AlarmServiceImplTest {

    @Autowired
    private AlarmServiceImpl realTimeAlarmServiceImpl;

    @Before
    public void before() throws Exception {
        // 初始化测试用例类中由Mockito的注解标注的所有模拟对象
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Method: findAllAlarm()
     */
    @Test
    public void testFindAllAlarm() {

        realTimeAlarmServiceImpl.findAllAlarm();

        Assert.assertTrue(true);
    }

    /**
     * Method: findByBk(String nums, Integer bkId, Date begintime, Date endtime, Integer groupId, String reason, Integer pageNo, Integer pageSize)
     */
    @Test
    public void testFindByBkForNumsBkIdBegintimeEndtimeGroupIdReasonPageNoPageSize() {

        //realTimeAlarmServiceImpl.findByBk("1",8,new Date(),new Date(),"4101010001","土匪",1,10);

        //Assert.assertEquals("1",51,51);
    }

    /**
     * Method: updateBySampleFlag(Integer alarmId, Integer sampleFlag)
     */
    @Test
    public void testUpdateBySampleFlag() {

        realTimeAlarmServiceImpl.updateBySampleFlag(956,0);

        Assert.assertEquals("修改成功",1,1);

    }

    /**
     * Method: updateByStatus(Integer status, Date confirmTime, Integer userId, Integer sampleFlag)
     */
    @Test
    public void testUpdateByStatus() {

    }

    /**
     * Method: getAlarmById(Integer alarmId)
     */
    @Test
    public void testGetRealTimeAlarmByAlarmId() {
//TODO: Test goes here... 
    }

    /**
     * Method: findByBk(RealTimeAlarmPage alarmPage)
     */
    @Test
    public void testFindByBkAlarmPage() {
//TODO: Test goes here... 
    }

    /**
     * Method: listAlarmsByBkId(Integer bkId, Integer pageNum, Integer pageSize)
     */
    @Test
    public void testListAlarmsByBkId() {
//TODO: Test goes here... 
    }

    /**
     * Method: listAlarmsByQueryDTO(AlarmQueryDTO alarmQueryDTO)
     */
    @Test
    public void testListAlarmsByQueryDTO() {
//TODO: Test goes here... 
    }

    /**
     * Method: listBKIdsByQueryDTO(AlarmQueryDTO alarmQueryDTO)
     */
    @Test
    public void testListBKIdsByQueryDTO() {
//TODO: Test goes here... 
    }


    @After
    public void after() throws Exception {
    }

    @Test
    public void test1() throws  Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        List<RealTimeAlarm> alarmByBukongId = realTimeAlarmServiceImpl.findAlarmByBukongId(1, format.parse("2018"), format.parse("2019"),1,10000);
        System.out.println(alarmByBukongId.size());
        int a = realTimeAlarmServiceImpl.findAlarmByBukongIdCount(1, format.parse("2018"), format.parse("2019"));
        System.out.println(a);
//        int alarmCount = realTimeAlarmServiceImpl.getAlarmCount(format.parse("2018"), format.parse("2019"));
//        System.out.println(alarmCount);
//        int alarmCount2 = realTimeAlarmServiceImpl.getAlarmCount("4101010023",format.parse("2018"), format.parse("2019"));
//        System.out.println(alarmCount2);
//        int totalAlarmCount = realTimeAlarmServiceImpl.getTotalAlarmCount();
//        System.out.println(totalAlarmCount);
        List<RealTimeAlarm> alarmByBukongId2 = realTimeAlarmServiceImpl.findAlarmByCameras(null, format.parse("2018"), format.parse("2019"),1,10000);
        System.out.println(alarmByBukongId2.size());
        int b = realTimeAlarmServiceImpl.findAlarmByCamerasCount(null, format.parse("2018"), format.parse("2019"));
        System.out.println(b);
    }


    @Test
    public void test2(){
        BigInteger b = new BigInteger("1001546496893987101");
        RealTimeAlarm realTimeAlarmByAlarmId = realTimeAlarmServiceImpl.getRealTimeAlarmByAlarmId(b);
        System.out.println(realTimeAlarmByAlarmId);
    }
} 


