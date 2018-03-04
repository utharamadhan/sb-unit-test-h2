package com.rpramadhan.sbspringdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.rpramadhan.sbspringdata.model.AuthorCount;
import com.rpramadhan.sbspringdata.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
	
	@Query(value = "select new com.rpramadhan.sbspringdata.model.AuthorCount(b.author, count(b)) from book b group by b.author")
	public List<AuthorCount> countByAuthor();

}
