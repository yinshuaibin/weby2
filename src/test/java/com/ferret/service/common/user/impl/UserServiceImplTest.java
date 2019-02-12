package com.ferret.service.common.user.impl;

import com.ferret.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.*;

/**
 * UserServiceImpl Tester.
 *
 * @author xuyi
 * @version 1.0
 * @since 03/21/2018
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@ActiveProfiles("test")
public class UserServiceImplTest {

	@Mock
	private UserServiceImpl userService;

	@Before
	public void before() throws Exception {
		// 初始化测试用例类中由Mockito的注解标注的所有模拟对象
		MockitoAnnotations.initMocks(this);

		// user.setUserId(null);
		// user.setUsername("Girl");
		// user.setNickname("执行长");
		// user.setLoginIp("127.0.0.1");
		// user.setLastLogin(new Date());
		// user.setTelephone("15709870987");
		// user.setRoleId("410101000001");
		// user.setAreaId("411428");
		// user.setPassword("456678");
	}

	/**
	 * Method: findByUsername(String username)
	 */
	@Test
	public void testFindByUsername() {
		User user = new User();
		user.setUsername("test");
		// 设置预期返回值,相当于模拟了一个查询结果
		when(userService.findByUsername(anyString())).thenReturn(user);
		// 实际调用的方法
		User u = userService.findByUsername("test");
		// 验证是否调用了该方法
		verify(userService).findByUsername(anyString());
		// assert 判断.是否符合预期
		Assertions.assertThat(u).isNotNull();
		Assertions.assertThat(user.getUsername()).isEqualTo("test");
	}

	/**
	 * Method: saveUser(User user)
	 */
	@Test
	public void testSaveUser() {
		User user = new User();
		user.setUsername("test");
		user.setEnabled(1);
		user.setAreaId("411401");
		when(userService.saveUser(user)).thenReturn(new User());
		User retUser = userService.saveUser(user);
		verify(userService).saveUser(user);
		Assertions.assertThat(retUser).isNotNull();
		Assertions.assertThat(user.getUsername()).isEqualTo("test");
	}

	/**
	 * Method: updateUser(User user)
	 */
	@Test
	public void testUpdateUser() {
		User user = new User();
		user.setUsername("Girl");
		user.setNickname("白浅");
		doNothing().when(userService).updateUser(user);
		userService.updateUser(user);
		verify(userService, times(1)).updateUser(user);
	}

	/**
	 * Method: deleteUser(Integer userId)
	 */
	@Test
	public void testDeleteUser() {
		doNothing().when(userService).deleteUser(anyString());
		userService.deleteUser("6");
		verify(userService).deleteUser("6");
	}

	/**
	 * Method: findCount()
	 */
	@Test
	public void testFindCount() {
		// when(userService.findCount()).thenReturn(100);
		// int i = userService.findCount();
		// verify(userService).findCount();
		// Assert.assertEquals("统计总数不正确",100,i);
	}

	/**
	 * Method: findAllUser(Integer pageNo, Integer pageSize)
	 */
	@Test
	public void testFindAllUser() {

		// List<User> users =
		// userService.findAllUser(1, 10);
	}

	@After
	public void after() throws Exception {

		log.debug("test finished");
	}
}
