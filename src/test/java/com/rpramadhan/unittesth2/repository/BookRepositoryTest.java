package com.rpramadhan.unittesth2.repository;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.rpramadhan.unittesth2.SpringBootH2Test;
import com.rpramadhan.unittesth2.model.Book;



@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations  = "classpath:application-test.properties")
@SpringBootTest(classes = SpringBootH2Test.class)
public class BookRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private BookRepository repository;
	
	@Test
	public void testSuccess() {
		
		String title = "unittesttitle";
		String author = "unittestauthor";
		
		Book dummyBook = Book.createInstance(title, author);
		entityManager.persist(dummyBook);
		entityManager.flush();
		
		Book resultBook = repository.findOne(dummyBook.getId());
		Assert.assertEquals(title, resultBook.getTitle());
		Assert.assertEquals(author, resultBook.getAuthor());
		
	}
	
}
