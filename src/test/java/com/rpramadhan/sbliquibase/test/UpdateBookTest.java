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
import com.rpramadhan.sbspringdata.service.IBookService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SBSpringDataTest.class)
@TestPropertySource(locations  = "classpath:application-test.properties")
public class UpdateBookTest {
	
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
	public void putSuccess() throws Exception {
		
		//dummy title and author
		String title = "How To Win Friends And Influence People";
		String author = "Dale Carnegie";
		
		//create new book
		Book newBook = Book.createInstance(title, author);
		bookService.saveOrUpdate(newBook);
		
		//validate update book rest api
		Long _id = newBook.getId();
		String _title = "How to Stop Worrying and Start Living";
		String _author = "Dale Carnegie";
		Book book = Book.createInstance(_title, _author);	
		mockMvc.perform(MockMvcRequestBuilders.put("/"+_id).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(book)))
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
	public void putFailure() throws Exception {
		
		//validate update book rest api
		Long _id = 10L;
		String _title = "How to Stop Worrying and Start Living";
		String _author = "Dale Carnegie";
		Book book = Book.createInstance(_title, _author);
		mockMvc.perform(MockMvcRequestBuilders.put("/"+_id).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(book)))
			.andExpect(new ResultMatcher() {
				@Override
				public void match(MvcResult result) throws Exception {
					Response response = mapper.readValue(result.getResponse().getContentAsString(), Response.class);
					Assert.assertEquals(Response.RC_NOT_FOUND, response.getResponseCode());
					Assert.assertEquals("not found", response.getResponseDesc());
				}
			});
		
	}

}
