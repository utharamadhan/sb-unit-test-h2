package com.rpramadhan.sbspringdata.model;

import java.io.Serializable;

public class AuthorCount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3415366711270700925L;
	
	public AuthorCount(String author, Long count) {
		this.author = author;
		this.count = count;
	}
	
	private String author;
	
	private Long count;
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}

}
