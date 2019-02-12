package com.ferret.controller; 

import lombok.extern.slf4j.Slf4j;
import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/** 
* ImageFeatureController Tester. 
* 
* @author xuyi
* @since 03/21/2018
* @version 1.0 
*/ 
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ImageFeatureControllerTest { 

@Before
public void before() throws Exception { 
} 

/** 
* 
* Method: makeFeature(@RequestBody Base64ImageEntity imageEntity) 
* 
*/ 
@Test
public void testMakeFeature() {
//TODO: Test goes here... 
} 

/** 
* 
* Method: getFeatureTask(@RequestBody DynamicQueryEntity entity) 
* 
*/ 
@Test
public void testGetFeatureTask() {
//TODO: Test goes here... 
} 

/** 
* 
* Method: getQueryProcessingByTaskId(@PathVariable("id") String task) 
* 
*/ 
@Test
public void testGetQueryProcessingByTaskId() {
//TODO: Test goes here... 
} 



@After
public void after() throws Exception { 
}
} 


