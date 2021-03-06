package com.rpramadhan.unittesth2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rpramadhan.unittesth2.model.AuthorCount;
import com.rpramadhan.unittesth2.model.Book;
import com.rpramadhan.unittesth2.repository.BookRepository;

@Component
public class BookService implements IBookService {
	
	@Autowired
	private BookRepository repository;
	
	@Override
	public Book findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public void saveOrUpdate(Book book) throws Exception {
		if (book.getId() != null && !isExists(book.getId())) {
			throw new Exception("not found");
		}
		repository.save(book);
	}
	
	private Boolean isExists(Long id) {
		return findById(id) != null;
	}

	@Override
	public List<AuthorCount> countByAuthor() {
		return repository.countByAuthor();
	}
	
}
