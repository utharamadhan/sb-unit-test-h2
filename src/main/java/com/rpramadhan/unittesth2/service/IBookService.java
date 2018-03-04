package com.rpramadhan.unittesth2.service;

import java.util.List;

import com.rpramadhan.unittesth2.model.AuthorCount;
import com.rpramadhan.unittesth2.model.Book;

public interface IBookService {
	
	public Book findById(Long id);
	
	public void saveOrUpdate(Book book) throws Exception;
	
	public List<AuthorCount> countByAuthor();

}
