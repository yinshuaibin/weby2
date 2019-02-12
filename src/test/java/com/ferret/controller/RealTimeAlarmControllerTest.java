package com.ferret.controller; 

import lombok.extern.slf4j.Slf4j;
import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/** 
* RealTimeAlarmController Tester. 
* 
* @author xuyi
* @since 03/21/2018
* @version 1.0 
*/ 
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RealTimeAlarmControllerTest { 

@Before
public void before() throws Exception { 
} 

/** 
* 
* Method: doFindAllObject() 
* 
*/ 
@Test
public void testDoFindAllObject() {
//TODO: Test goes here... 
} 

/** 
* 
* Method: doFindByBk(@RequestBody RealTimeAlarmPage alarmPage) 
* 
*/ 
@Test
public void testDoFindByBk() {
//TODO: Test goes here... 
} 

/** 
* 
* Method: listAlarmsByBkId(@PathVariable("bkId") Integer bkId, Integer pageNum, Integer pageSize) 
* 
*/ 
@Test
public void testListAlarmsByBkId() {
//TODO: Test goes here... 
} 

/** 
* 
* Method: listAlarmsBySearch(@RequestParam String queryDtoStr) 
* 
*/ 
@Test
public void testListAlarmsBySearch() {
//TODO: Test goes here... 
} 

/** 
* 
* Method: listBKIdsByQueryDTO(@RequestParam String queryDtoStr) 
* 
*/ 
@Test
public void testListBKIdsByQueryDTO() {
//TODO: Test goes here... 
} 

/** 
* 
* Method: doUpdateByStatus(@RequestParam Integer status, @RequestParam Date confirmTime, @RequestParam Integer userId, @RequestParam Integer sampleFlag) 
* 
*/ 
@Test
public void testDoUpdateByStatus() {
//TODO: Test goes here... 
} 

/** 
* 
* Method: doUpdateBySampleFlag(@PathVariable("id") Integer alarmId, Integer sampleFlag) 
* 
*/ 
@Test
public void testDoUpdateBySampleFlag() {
//TODO: Test goes here... 
} 



@After
public void after() throws Exception { 
}
} 


