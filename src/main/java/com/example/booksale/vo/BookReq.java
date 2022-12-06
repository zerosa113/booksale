package com.example.booksale.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookReq {

	private String isbn;

	private String name;

	private String author;

	private int price;

	private int inventory;

	private int sales;

	private String genre;

	@JsonProperty("bookSale")
	private Integer bookSale;
	@JsonProperty("quantity")
	private Integer quantity;

	public BookReq() {

	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Integer getBooksale() {
		return bookSale;
	}

	public void setBooksale(Integer booksale) {
		this.bookSale = booksale;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
