package com.rpramadhan.sbspringdata.service;

import java.util.List;

import com.rpramadhan.sbspringdata.model.AuthorCount;
import com.rpramadhan.sbspringdata.model.Book;

public interface IBookService {
	
	public Book findById(Long id);
	
	public void saveOrUpdate(Book book) throws Exception;
	
	public List<AuthorCount> countByAuthor();

}
