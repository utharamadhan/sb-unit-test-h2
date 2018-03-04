package com.rpramadhan.sbspringdata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name="book")
@Table(name="book")
public class Book implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6358381915366283196L;
	
	private Book() {}
	
	public static Book createInstance(Long id, String title, String author) {
		Book book = new Book();
		book.setId(id);
		book.setTitle(title);
		book.setAuthor(author);
		return book;
	}
	
	public static Book createInstance(String title, String author) {
		return Book.createInstance(null, title, author);
	}
	
	@Id
	@JsonProperty("id")
	@SequenceGenerator(name = "book_pk_book_seq", sequenceName = "book_pk_book_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_pk_book_seq")
	@Column(name = "pk_book", unique = true, nullable = false)
	private Long id;
	
	
	@JsonProperty("title")
	@Column(name = "title")
	private String title;
	
	@JsonProperty("author")
	@Column(name = "author")
	private String author;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}

}
