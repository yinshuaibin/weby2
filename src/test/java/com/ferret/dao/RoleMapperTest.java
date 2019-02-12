/**
 * 
 */
package com.ferret.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.ferret.bean.Role;

import lombok.extern.slf4j.Slf4j;

/**
 * @author bin
 *
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleMapperTest {

	
	@Autowired
	private RoleMapper roleMapper;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Test method for {@link com.ferret.dao.RoleMapper#insertOne(com.ferret.bean.Role)}.
	 */
	@Test
	public void testInsertOne() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ferret.dao.RoleMapper#deleteById(java.lang.String)}.
	 */
	@Test
	public void testDeleteById() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ferret.dao.RoleMapper#updateById(com.ferret.bean.Role)}.
	 */
	@Test
	public void testUpdateById() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ferret.dao.RoleMapper#selectAll()}.
	 */
	@Test
	public void testSelectAll() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ferret.dao.RoleMapper#selectByRoleArea(java.lang.String)}.
	 */
	@Test
	public void testSelectByRoleArea() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ferret.dao.RoleMapper#selectCountByRoleArea(java.lang.String)}.
	 */
	@Test
	public void testSelectCountByRoleArea() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ferret.dao.RoleMapper#selectAllCount()}.
	 */
	@Test
	public void testSelectAllCount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ferret.dao.RoleMapper#selectByRoleId(java.lang.String)}.
	 */
	@Test
	public void testSelectByRoleId() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ferret.dao.RoleMapper#selectRoleByRoleName(java.lang.String)}.
	 */
	@Test
	public void testSelectRoleByRoleName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ferret.dao.RoleMapper#getRoleByPage(java.lang.Integer, java.lang.Integer)}.
	 */
	@Test
	public void testGetRoleByPage() {
		List<Role> role = roleMapper.getRoleByPage(1, 15);
		//System.out.println(role);
		log.debug(JSON.toJSONString(role));
	}

	/**
	 * Test method for {@link com.ferret.dao.RoleMapper#getRoleByPageAndRoleId(java.lang.Integer, java.lang.Integer, java.lang.String)}.
	 */
	@Test
	public void testGetRoleByPageAndRoleId() {
		fail("Not yet implemented");
	}

}
