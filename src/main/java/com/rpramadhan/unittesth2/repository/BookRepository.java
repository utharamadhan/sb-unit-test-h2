package com.rpramadhan.unittesth2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rpramadhan.unittesth2.model.AuthorCount;
import com.rpramadhan.unittesth2.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	
	@Query(value = "select new com.rpramadhan.unittesth2.model.AuthorCount(b.author, count(b)) from book b group by b.author")
	public List<AuthorCount> countByAuthor();

}
