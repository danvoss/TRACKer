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

	@Test
	public void contextLoads() {
	}

	@Test
	public void testLogin() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/login")
				.param("name", "TestUser")
				.param("password", "pw")
		);
		Assert.assertTrue(users.count() == 1);
	}

	@Test
	public void testCreate() {

	}

}
