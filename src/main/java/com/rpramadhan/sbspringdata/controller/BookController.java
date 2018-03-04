package com.rpramadhan.sbspringdata.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rpramadhan.sbspringdata.model.AuthorCount;
import com.rpramadhan.sbspringdata.model.Book;
import com.rpramadhan.sbspringdata.model.Response;
import com.rpramadhan.sbspringdata.service.IBookService;

@RestController
public class BookController {
	
	@Autowired
	private IBookService service;
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<Response> findById(@PathVariable("id") Long id) {
		Response resp = new Response();
		Book book = service.findById(id);
		if (book != null) {
			resp.setResponseCode(Response.RC_SUCCESS);
			resp.setResponseDesc(Response.RD_SUCCESS);
			resp.setResult(book);
			return ResponseEntity.ok().body(resp);
		} else {
			resp.setResponseCode(Response.RC_NOT_FOUND);
			resp.setResponseDesc(Response.RD_NOT_FOUND);
			return ResponseEntity.badRequest().body(resp);
		}
	}
	
	@RequestMapping(value = "/", method=RequestMethod.POST)
	public ResponseEntity<Response> post(@RequestBody Book book) {
		Response resp = new Response();
		try {
			validateBook(book);
			service.saveOrUpdate(book);
			resp.setResponseCode(Response.RC_SUCCESS);
			resp.setResponseDesc(Response.RD_SUCCESS);
			resp.setResult(book);
			return ResponseEntity.ok().body(resp);
		} catch (IllegalArgumentException argEx){
			resp.setResponseCode(Response.RC_ERROR);
			resp.setResponseDesc(argEx.getMessage());
		} catch (Exception ex) {
			resp.setResponseCode(Response.RC_NOT_FOUND);
			resp.setResponseDesc(ex.getMessage());
		}
		return ResponseEntity.badRequest().body(resp);
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Response> put(@PathVariable("id") Long id, @RequestBody Book book) {
		Response resp = new Response();
		try {
			validateEditBook(id, book);
			service.saveOrUpdate(book);
			resp.setResponseCode(Response.RC_SUCCESS);
			resp.setResponseDesc(Response.RD_SUCCESS);
			resp.setResult(book);
			return ResponseEntity.accepted().body(resp);
		} catch (IllegalArgumentException argEx){
			resp.setResponseCode(Response.RC_ERROR);
			resp.setResponseDesc(argEx.getMessage());
		} catch (Exception ex) {
			resp.setResponseCode(Response.RC_NOT_FOUND);
			resp.setResponseDesc(ex.getMessage());
		}
		return ResponseEntity.badRequest().body(resp);
	}
	
	@RequestMapping(value = "/countByAuthor", method=RequestMethod.GET)
	public ResponseEntity<Response> countByAuthor() {
		Response resp = new Response();
		List<AuthorCount> authorCount = service.countByAuthor();
		if (authorCount != null) {
			resp.setResponseCode(Response.RC_SUCCESS);
			resp.setResponseDesc(Response.RD_SUCCESS);
			resp.setResult(authorCount);
			return ResponseEntity.ok().body(resp);
		} else {
			resp.setResponseCode(Response.RC_NOT_FOUND);
			resp.setResponseDesc(Response.RD_NOT_FOUND);
			return ResponseEntity.badRequest().body(resp);
		}
	}
	
	private void validateEditBook(Long id, Book book) throws IllegalArgumentException {
		if (id == null) {
			throw new IllegalArgumentException("id is required");
		} else {
			book.setId(id);
		}
		validateBook(book);
	}
	
	private void validateBook(Book book) throws IllegalArgumentException {
		if (StringUtils.isEmpty(book.getTitle())) {
			throw new IllegalArgumentException("title is required");
		} else if(StringUtils.isEmpty(book.getAuthor())) {
			throw new IllegalArgumentException("author is required");
		}
	}
	
}
