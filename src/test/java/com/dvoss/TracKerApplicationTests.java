package com.dvoss;

import com.dvoss.services.MeetRepository;
import com.dvoss.services.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TracKerApplication.class)
@WebAppConfiguration
public class TracKerApplicationTests {

	@Autowired
	WebApplicationContext wap;

	@Autowired
	UserRepository users;

	@Autowired
	MeetRepository meets;

	MockMvc mockMvc;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
	}

//	@Test
//	public void contextLoads() {
//	}

	@Test
	public void testLogin() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/login")
				.param("username", "TestUser")
				.param("password", "pw")
		);
		Assert.assertTrue(users.count() == 1);
	}

	@Test
	public void testCreate() throws Exception {
		testLogin();

		mockMvc.perform(
				MockMvcRequestBuilders.post("/create")
				.param("date", LocalDate.now().toString())
				.param("location", "TestLocation")
				.param("division", "TestDivision")
				.param("gender", "TestGender")
				.param("winner", "TestWinner")
				.param("comments", "TestComments")
				.sessionAttr("username", "TestUser")
		);
		Assert.assertTrue(meets.count() == 1);
	}

	//  -- how do I get the id? :
	@Test
	public void testDelete() throws Exception {
		testLogin();
		testCreate();
		mockMvc.perform(
				MockMvcRequestBuilders.post("/delete")
				.sessionAttr("username", "TestUser")
				//.param("id", ?)
		);
		Assert.assertTrue(meets.count() == 0);
	}

	@Test
	public void testUpdate() throws Exception {
		testLogin();
		testCreate();
		mockMvc.perform(
				MockMvcRequestBuilders.post("/update")
				.param("date", LocalDate.now().toString())
				.param("location", "NewLoc")
				.param("division", "NewDiv")
				.param("gender", "NewGen")
				.param("winner", "NewWin")
				.param("comments", "NewCom")
				.sessionAttr("username", "TestUser")
				//.param("id", ? )
		);
		Assert.assertTrue(meets.count() == 1);
	}

}
