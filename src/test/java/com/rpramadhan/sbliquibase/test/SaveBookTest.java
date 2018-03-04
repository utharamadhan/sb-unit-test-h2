package com.rpramadhan.sbliquibase.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpramadhan.sbspringdata.main.SBSpringDataTest;
import com.rpramadhan.sbspringdata.model.Book;
import com.rpramadhan.sbspringdata.model.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SBSpringDataTest.class)
@TestPropertySource(locations  = "classpath:application-test.properties")
public class SaveBookTest {
	
	private MockMvc mockMvc;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
    private WebApplicationContext wac;
	
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void postSuccess() throws Exception {
		
		//dummy title and author
		String _title = "Talent Is Overrated";
		String _author = "Geoff Colvin";
		
		//validate create book rest api
		Book book = Book.createInstance(_title, _author);
		mockMvc.perform(MockMvcRequestBuilders.post("/").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(book)))
			.andExpect(new ResultMatcher() {
				@Override
				public void match(MvcResult result) throws Exception {
					Response response = mapper.readValue(result.getResponse().getContentAsString(), Response.class);
					Assert.assertEquals(Response.RC_SUCCESS, response.getResponseCode());
					Assert.assertEquals(Response.RD_SUCCESS, response.getResponseDesc());
					Book book = mapper.readValue(mapper.writeValueAsString(response.getResult()), Book.class);
					Assert.assertNotNull(book.getId());
					Assert.assertEquals(_title, book.getTitle());
					Assert.assertEquals(_author, book.getAuthor());
				}
			});
		
	}
	
	@Test
	public void postFailure() throws Exception {
		
		//validate create book rest api with empty title
		String _author = "Charles Duhigg";
		Book _firstBook = Book.createInstance(null, _author);
		mockMvc.perform(MockMvcRequestBuilders.post("/").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(_firstBook)))
			.andExpect(new ResultMatcher() {
				@Override
				public void match(MvcResult result) throws Exception {
					Response response = mapper.readValue(result.getResponse().getContentAsString(), Response.class);
					Assert.assertEquals(Response.RC_ERROR, response.getResponseCode());
					Assert.assertEquals("title is required", response.getResponseDesc());
				}
			});
		
		//validate create book rest api with empty author
		String _title = "The Power of Habit";
		Book _secondBook = Book.createInstance(_title, null);
		mockMvc.perform(MockMvcRequestBuilders.post("/").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(_secondBook)))
		.andExpect(new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				Response response = mapper.readValue(result.getResponse().getContentAsString(), Response.class);
				Assert.assertEquals(Response.RC_ERROR, response.getResponseCode());
				Assert.assertEquals("author is required", response.getResponseDesc());
			}
		});
		
	}

}
