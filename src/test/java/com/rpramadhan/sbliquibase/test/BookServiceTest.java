package com.rpramadhan.sbliquibase.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.rpramadhan.sbspringdata.repository.BookRepository;
import com.rpramadhan.sbspringdata.service.BookService;
import com.rpramadhan.sbspringdata.service.IBookService;

@RunWith(SpringRunner.class)
public class BookServiceTest {

	@TestConfiguration
	static class BookServiceTestContextLoader {
		
		@Bean
		public IBookService bookService() {
			return new BookService();
		}
		
	}
	
	@Autowired
	private IBookService bookService;
	
	@MockBean
	private BookRepository repository;
	
	@Test
	public void testSuccess() {
		System.out.println(bookService);
		System.out.println(repository);
	}
	
}
