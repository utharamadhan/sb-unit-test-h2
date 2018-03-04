package com.rpramadhan.unittesth2.endtoend;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpramadhan.unittesth2.SpringBootH2Test;
import com.rpramadhan.unittesth2.model.Book;
import com.rpramadhan.unittesth2.model.Response;
import com.rpramadhan.unittesth2.service.IBookService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootH2Test.class)
@TestPropertySource(locations  = "classpath:application-test.properties")
public class GetBookTest {
	
	private MockMvc mockMvc;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
	private IBookService bookService;
	
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void findByIdSuccess() throws Exception {
		
		//dummy title and author
		String title = "How To Win Friends And Influence People";
		String author = "Dale Carnegie";
		
		//create new book
		Book newBook = Book.createInstance(title, author);
		bookService.saveOrUpdate(newBook);
		
		//validate get book by id rest api
		Long _testId = newBook.getId();
		mockMvc.perform(MockMvcRequestBuilders.get("/"+_testId))
			.andExpect(new ResultMatcher() {
				@Override
				public void match(MvcResult result) throws Exception {
					Response response = mapper.readValue(result.getResponse().getContentAsString(), Response.class);
					Assert.assertEquals(Response.RC_SUCCESS, response.getResponseCode());
					Assert.assertEquals("success", response.getResponseDesc());
					Book book = mapper.readValue(mapper.writeValueAsString(response.getResult()), Book.class);
					Assert.assertEquals(title, book.getTitle());
					Assert.assertEquals(author, book.getAuthor());
				}
			});
		
	}
	
	@Test
	public void findByIdNotFound() throws Exception {
		Long _testId = -1L;
		mockMvc.perform(MockMvcRequestBuilders.get("/"+_testId))
			.andExpect(new ResultMatcher() {
				@Override
				public void match(MvcResult result) throws Exception {
					Response response = mapper.readValue(result.getResponse().getContentAsString(), Response.class);
					Assert.assertEquals("04", response.getResponseCode());
					Assert.assertEquals("not found", response.getResponseDesc());
				}
			});
	}
	
}
