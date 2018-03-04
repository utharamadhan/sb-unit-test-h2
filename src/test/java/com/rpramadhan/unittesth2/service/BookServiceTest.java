package com.rpramadhan.unittesth2.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.rpramadhan.unittesth2.model.Book;
import com.rpramadhan.unittesth2.repository.BookRepository;

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
	public void testSuccess() throws Exception {
		
		String title = "unittesttitle";
		String author = "unittestauthor";
		
		Book dummyBook = Book.createInstance(1L, title, author);
		bookService.saveOrUpdate(dummyBook);
		
		Mockito.when(repository.findOne(dummyBook.getId())).thenReturn(dummyBook);
		
		Book resultBook = bookService.findById(dummyBook.getId());
		Assert.assertEquals(title, resultBook.getTitle());
		Assert.assertEquals(author, resultBook.getAuthor());
		
	}
	
}
