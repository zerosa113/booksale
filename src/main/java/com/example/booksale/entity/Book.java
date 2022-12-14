package com.example.booksale.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book {

	@Id
	@Column(name = "isbn")
	private String isbn;

	@Column(name = "name")
	private String name;

	@Column(name = "author")
	private String author;

	@Column(name = "price")
	private int price;

	@Column(name = "inventory")
	private int inventory;

	@Column(name = "sales")
	private int sales;

	@Column(name = "genre")
	private String genre;

	public Book() {

	}

	public Book(String isbn, String name, String author, int price, int inventory, int sales, String genre) {
		this.name = name;
		this.isbn = isbn;
		this.author = author;
		this.price = price;
		this.inventory = inventory;
		this.sales = sales;
		this.genre = genre;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
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

}
