package com.ferret.controller; 

import lombok.extern.slf4j.Slf4j;
import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/** 
* UserController Tester. 
* 
* @author xuyi
* @since 03/21/2018
* @version 1.0 
*/ 
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest { 

@Before
public void before() throws Exception { 
} 

/** 
* 
* Method: doInsertUser(@RequestBody User user) 
* 
*/ 
@Test
public void testDoInsertUser() {
//TODO: Test goes here... 
} 

/** 
* 
* Method: doUpdateUser(@RequestBody User user) 
* 
*/ 
@Test
public void testDoUpdateUser() {
//TODO: Test goes here... 
} 

/** 
* 
* Method: doDeleteUser(@PathVariable("userId") Integer userId) 
* 
*/ 
@Test
public void testDoDeleteUser() {
//TODO: Test goes here... 
} 

/** 
* 
* Method: doFindAllUser(@RequestParam(value = "pageNo",required = false) Integer pageNo, @RequestParam(value = "pageSize",required = false) Integer pageSize) 
* 
*/ 
@Test
public void testDoFindAllUser() {
//TODO: Test goes here... 
} 

/** 
* 
* Method: userCount() 
* 
*/ 
@Test
public void testUserCount() {
//TODO: Test goes here... 
} 

/** 
* 
* Method: doFindByUsername(@RequestParam(value="username",required = true) String username) 
* 
*/ 
@Test
public void testDoFindByUsername() {
//TODO: Test goes here... 
} 



@After
public void after() throws Exception { 
}
} 


