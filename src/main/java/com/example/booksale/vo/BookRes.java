package com.example.booksale.vo;

import java.util.List;
import java.util.Set;

import com.example.booksale.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookRes {

	@JsonProperty("book_info")
	private Book book;

	private String isbn;

	private String name;

	private String author;

	private Integer price;

	private Integer sales;

	private Integer inventory;

	private String genre;

	private Integer quantity;

	private Integer buyPrice;

	private String message;

	private List<String> messageList;

	private List<Book> bookList;

	private Set<Book> bookSet;

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public BookRes() {

	}

	public BookRes(String name, String isbn, String author, Integer price, Integer sales, Integer inventory,
			String genre) {
		this.name = name;
		this.isbn = isbn;
		this.author = author;
		this.price = price;
		this.sales = sales;
		this.inventory = inventory;
	}

	public BookRes(String name, String isbn, String author, Integer price) {
		this.name = name;
		this.isbn = isbn;
		this.author = author;
		this.price = price;
	}

	public BookRes(String name, String isbn, String author, Integer price, Integer sales, Integer inventory) {
		this.name = name;
		this.isbn = isbn;
		this.author = author;
		this.price = price;
		this.sales = sales;
		this.inventory = inventory;
	}

	public BookRes(String name, String isbn, String author, Integer price, Integer inventory) {
		this.name = name;
		this.isbn = isbn;
		this.author = author;
		this.price = price;
		this.inventory = inventory;
	}

	public BookRes(String name, Integer price, String isbn, String author, Integer quantity, Integer buyPrice) {
		this.name = name;
		this.price = price;
		this.isbn = isbn;
		this.author = author;
		this.quantity = quantity;
		this.buyPrice = buyPrice;
	}

	public BookRes(String message) {
		this.message = message;
	}

	public BookRes(Book book, String message) {
		this.book = book;
		this.message = message;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Integer buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public List<String> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}

	public Set<Book> getBookSet() {
		return bookSet;
	}

	public void setBookSet(Set<Book> bookSet) {
		this.bookSet = bookSet;
	}

}
