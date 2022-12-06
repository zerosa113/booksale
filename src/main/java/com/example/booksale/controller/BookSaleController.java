package com.example.booksale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.booksale.vo.BookBuyReq;
import com.example.booksale.vo.BookListRes;
import com.example.booksale.vo.BookReq;
import com.example.booksale.vo.BookRes;
import com.example.booksale.vo.BookfindReq;
import com.example.booksale.entity.Book;
import com.example.booksale.service.ifs.BookSaleService;

@CrossOrigin
@RestController
public class BookSaleController {

	@Autowired
	private BookSaleService bookSaleService;

	// 1.建立書籍資料
	@PostMapping(value = "/api/createBook")
	public BookRes createBook(@RequestBody BookReq req) {
		return bookSaleService.createBook(req);
	}

	// 2.透過書籍類別搜尋書籍資料
	@PostMapping(value = "/api/bookGenreSearch")
	public BookRes bookGenreSearch(@RequestBody BookReq req) {
		return bookSaleService.bookGenreSearch(req.getGenre());
	}

	// 3.消費者搜尋書籍
	@PostMapping(value = "/api/bookSearchConsumer")
	public BookListRes bookSearchConsumer(@RequestBody BookfindReq bookreq) {
		return bookSaleService.bookSearchConsumer(bookreq.getIsbnOrNameOrAuthor());
	}

	// 4.書籍商搜尋書籍
	@PostMapping(value = "/api/bookSearchSeller")
	public BookListRes bookSearchSeller(@RequestBody BookfindReq bookreq) {
		return bookSaleService.bookSearchSeller(bookreq.getIsbnOrNameOrAuthor());
	}

	// 5.更新書籍資料-庫存量(進貨)
	@PostMapping(value = "/api/updateInventory")
	public BookRes updateInventory(@RequestBody BookReq req) {

		return bookSaleService.updateInventory(req.getIsbn(), req.getBooksale());
	}

	// 6.更新書籍資料-更新書籍價格
	@PostMapping(value = "/api/updatePrice")
	public BookRes updatePrice(@RequestBody BookReq req) {
		return bookSaleService.updatePrice(req.getIsbn(), req.getPrice());
	}

	// 7.購買書籍
	@PostMapping(value = "/api/buyBook")
	public BookListRes buyBook(@RequestBody BookBuyReq bookBuyList) {
		return bookSaleService.buyBook(bookBuyList);
	}

	// 8.書籍銷售量排行前5
	@PostMapping(value = "/api/booksaleTop5")
	public List<Book> booksaleTop5() {
		return bookSaleService.bookTop5();
	}
}
