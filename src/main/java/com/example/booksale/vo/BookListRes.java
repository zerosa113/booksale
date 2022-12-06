package com.example.booksale.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookListRes {

	private List<BookRes> bookResList;

	private String message;

	private Integer totalPrice;

	public BookListRes() {

	}

	public BookListRes(String message) {
		this.message = message;
	}

	public List<BookRes> getBookResList() {
		return bookResList;
	}

	public void setBookResList(List<BookRes> bookResList) {
		this.bookResList = bookResList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

}
