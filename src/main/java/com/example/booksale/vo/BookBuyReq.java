package com.example.booksale.vo;

import java.util.List;

public class BookBuyReq {
	
	private String isbn;

	private Integer quantity;

	private List<BookBuyReq> bookBuyList;
	
	public BookBuyReq() {
		
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public List<BookBuyReq> getBookBuyList() {
		return bookBuyList;
	}

	public void setBookBuyList(List<BookBuyReq> bookBuyList) {
		this.bookBuyList = bookBuyList;
	}
	
}
