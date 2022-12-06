package com.example.booksale.service.ifs;

import java.util.List;

import com.example.booksale.vo.BookBuyReq;
import com.example.booksale.vo.BookListRes;
import com.example.booksale.vo.BookReq;
import com.example.booksale.vo.BookRes;
import com.example.booksale.entity.Book;

public interface BookSaleService {

	public BookRes createBook(BookReq req);

	public BookRes bookGenreSearch(String genre);

	public BookListRes bookSearchConsumer(String isbnOrNameOrAuthor);

	public BookListRes bookSearchSeller(String isbnOrNameOrAuthor);

	public BookRes updateInventory(String isbn, Integer bookPurchase);

	public BookRes updatePrice(String isbn, int price);

	public BookListRes buyBook(BookBuyReq bookBuyList);

	public List<Book> bookTop5();
}
