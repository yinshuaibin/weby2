package com.ferret.controller; 

import lombok.extern.slf4j.Slf4j;
import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/** 
* CityController Tester. 
* 
* @author xuyi
* @since 03/21/2018
* @version 1.0 
*/ 
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class CityControllerTest { 

@Before
public void before() throws Exception { 
} 

/** 
* 
* Method: citys(@PathVariable(value = "code") Integer code, Boolean needGroup, Boolean needInfo) 
* 
*/ 
@Test
public void testCitys() {
//TODO: Test goes here... 
} 


/** 
* 
* Method: getCameraInfo(List<Map<String, Object>> cameraGroupTree, Boolean needInfo) 
* 
*/ 
@Test
public void testGetCameraInfo() {
//TODO: Test goes here... 
/* 
try { 
   Method method = CityController.getClass().getMethod("getCameraInfo", List<Map<String,.class, Boolean.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 
/** 
* 
* Method: getCameraGroup(List<Map<String, Object>> cityTree, Boolean needGroup, Boolean needInfo) 
* 
*/ 
@Test
public void testGetCameraGroup() {
//TODO: Test goes here... 
/* 
try { 
   Method method = CityController.getClass().getMethod("getCameraGroup", List<Map<String,.class, Boolean.class, Boolean.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

@After
public void after() throws Exception { 
}
} 


