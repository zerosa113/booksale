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

	// 1.�إ߮��y���
	@PostMapping(value = "/api/createBook")
	public BookRes createBook(@RequestBody BookReq req) {
		return bookSaleService.createBook(req);
	}

	// 2.�z�L���y���O�j�M���y���
	@PostMapping(value = "/api/bookGenreSearch")
	public BookRes bookGenreSearch(@RequestBody BookReq req) {
		return bookSaleService.bookGenreSearch(req.getGenre());
	}

	// 3.���O�̷j�M���y
	@PostMapping(value = "/api/bookSearchConsumer")
	public BookListRes bookSearchConsumer(@RequestBody BookfindReq bookreq) {
		return bookSaleService.bookSearchConsumer(bookreq.getIsbnOrNameOrAuthor());
	}

	// 4.���y�ӷj�M���y
	@PostMapping(value = "/api/bookSearchSeller")
	public BookListRes bookSearchSeller(@RequestBody BookfindReq bookreq) {
		return bookSaleService.bookSearchSeller(bookreq.getIsbnOrNameOrAuthor());
	}

	// 5.��s���y���-�w�s�q(�i�f)
	@PostMapping(value = "/api/updateInventory")
	public BookRes updateInventory(@RequestBody BookReq req) {

		return bookSaleService.updateInventory(req.getIsbn(), req.getBooksale());
	}

	// 6.��s���y���-��s���y����
	@PostMapping(value = "/api/updatePrice")
	public BookRes updatePrice(@RequestBody BookReq req) {
		return bookSaleService.updatePrice(req.getIsbn(), req.getPrice());
	}

	// 7.�ʶR���y
	@PostMapping(value = "/api/buyBook")
	public BookListRes buyBook(@RequestBody BookBuyReq bookBuyList) {
		return bookSaleService.buyBook(bookBuyList);
	}

	// 8.���y�P��q�Ʀ�e5
	@PostMapping(value = "/api/booksaleTop5")
	public List<Book> booksaleTop5() {
		return bookSaleService.bookTop5();
	}
}
