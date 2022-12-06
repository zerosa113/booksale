package com.example.booksale.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.booksale.vo.BookBuyReq;
import com.example.booksale.vo.BookListRes;
import com.example.booksale.vo.BookReq;
import com.example.booksale.vo.BookRes;
import com.example.booksale.constants.BookSaleRtnCode;
import com.example.booksale.entity.Book;
import com.example.booksale.repository.BookDao;
import com.example.booksale.service.ifs.BookSaleService;

@Service
public class BookSaleServiceImpl implements BookSaleService {

	@Autowired
	private BookDao bookDao;

	// 建立書籍資料
	@Override
	public BookRes createBook(BookReq req) {

		// 防呆check params
		BookRes res = new BookRes();
		res = checkParams(req);
		if (res != null) {
			return res;
		}

		Book book = new Book(req.getIsbn(), req.getName(), req.getAuthor(), req.getPrice(), req.getInventory(),
				req.getSales(), req.getGenre());
		// 確認主鍵不是null
		bookDao.existsById(req.getIsbn());
		// 將建立的資料存入資料庫
		bookDao.save(book);
		// 設定使用者輸入的資料
		BookRes newRes = new BookRes(req.getName(), req.getIsbn(), req.getAuthor(), req.getPrice(), req.getSales(),
				req.getInventory(), req.getGenre());
		// 回傳顯示給使用者看輸入的資料
		return newRes;
	}

	private BookRes checkParams(BookReq req) {
		// 確認ISBN是否有重複
		if (bookDao.findById(req.getIsbn()).isPresent()) {
			return new BookRes(BookSaleRtnCode.ISBN_EXISTED.getMessage());
		}
		// 確認輸入的字串是否為null
		if (!StringUtils.hasText(req.getIsbn())) {
			// 回傳message顯示isbn輸入錯誤
			BookRes res = new BookRes(BookSaleRtnCode.BOOK_ISBN_REQUIRED.getMessage());
			return res;
		}
		if (!StringUtils.hasText(req.getName())) {
			// 回傳message顯示書名輸入錯誤
			BookRes res = new BookRes(BookSaleRtnCode.BOOK_NAME_REQUIRED.getMessage());
			return res;
		}
		if (!StringUtils.hasText(req.getAuthor())) {
			// 回傳message顯示作者輸入錯誤
			BookRes res = new BookRes(BookSaleRtnCode.BOOK_AUTHOR_REQUIRED.getMessage());
			return res;
		}
		// 設定書籍價格不得為負
		if (req.getPrice() < 0) {
			// 回傳message顯示價格輸入錯誤
			BookRes res = new BookRes(BookSaleRtnCode.BOOK_PRICE_REQUIRED.getMessage());
			return res;
		}
		if (!StringUtils.hasText(req.getGenre())) {
			// 回傳message顯示類別輸入錯誤
			BookRes res = new BookRes(BookSaleRtnCode.BOOK_GENRE_REQUIRED.getMessage());
			return res;
		}

		return null;
	}

	// 透過書籍類型搜尋書籍資料
	@Override
	public BookRes bookGenreSearch(String genre) {
		// 防呆確認是否輸入正確
		if (!StringUtils.hasText(genre)) {
			return new BookRes(BookSaleRtnCode.BOOK_ISNOT_EXISTED.getMessage());
		}

		// 用逗號切割輸入的類型字串
		String[] bookArray = genre.split(",");
		// 建立Set用來存放書籍類型
		Set<String> genreSet = new HashSet<>();
		for (String book : bookArray) {
			// 去除空格
			String str = book.trim();
			// 放入Set會把重複的字串覆蓋掉
			genreSet.add(str);
		}

		// 從資料庫中找出所有資料存放進List
		List<Book> bookList = bookDao.findAll();
		// 建立Set用來存放書籍
		Set<Book> bookSet = new HashSet<>();

		// 將書籍的類別與資料庫的所有資料做比對
		for (String genreStr : genreSet) {
			for (Book book : bookList) {
				// 設定條件包含
				if (book.getGenre().contains(genreStr)) {
					// 把資料庫含有genre關鍵字的書加進Set裡
					bookSet.add(book);
				}
			}
		}

		// 回傳存在Set的書籍(多本)
		BookRes res = new BookRes();
		res.setBookSet(bookSet);
		return res;
	}

	// 消費者透過書籍名稱、ISBN或作者搜尋
	@Override
	public BookListRes bookSearchConsumer(String isbnOrNameOrAuthor) {
		List<BookRes> bookResList = new ArrayList<>();
		BookListRes consumer = new BookListRes();

		// 判斷輸入的書名是否為空
		if (!bookDao.findByName(isbnOrNameOrAuthor).isEmpty()) {
			// 使用findByName尋找相符合的資料
			List<Book> bookList = bookDao.findByName(isbnOrNameOrAuthor);
			// foreach bookList 將帶有資料的書籍個別抽出存入bookResList中
			for (Book book : bookList) {
				BookRes bookRes = new BookRes(book.getName(), book.getIsbn(), book.getAuthor(), book.getPrice());
				bookResList.add(bookRes);
			}

			consumer.setBookResList(bookResList);
			return consumer;

			// 判斷輸入的ISBN是否有相符合的資料
		} else if (bookDao.findById(isbnOrNameOrAuthor).isPresent()) {
			Optional<Book> bookId = bookDao.findById(isbnOrNameOrAuthor);
			BookRes bookRes = new BookRes(bookId.get().getName(), bookId.get().getIsbn(), bookId.get().getAuthor(),
					bookId.get().getPrice());
			bookResList.add(bookRes);

			consumer.setBookResList(bookResList);
			return consumer;

			// 判斷輸入的作者是否為空
		} else if (!bookDao.findByAuthor(isbnOrNameOrAuthor).isEmpty()) {
			List<Book> bookList = bookDao.findByAuthor(isbnOrNameOrAuthor);
			for (Book book : bookList) {
				BookRes bookRes = new BookRes(book.getName(), book.getIsbn(), book.getAuthor(), book.getPrice());
				bookResList.add(bookRes);
			}

			consumer.setBookResList(bookResList);
			return consumer;
		}

		// 若以上條件都不符合則回傳書籍不存在的訊息
		return new BookListRes(BookSaleRtnCode.BOOK_ISNOT_EXISTED.getMessage());
	}

	// 書籍商透過書籍名稱、ISBN或作者搜尋
	@Override
	public BookListRes bookSearchSeller(String isbnOrNameOrAuthor) {
		List<BookRes> bookFindSeller = new ArrayList<>();
		BookListRes seller = new BookListRes();

		// 判斷輸入的書名是否為空
		if (!bookDao.findByName(isbnOrNameOrAuthor).isEmpty()) {
			List<Book> bookList = bookDao.findByName(isbnOrNameOrAuthor);
			for (Book book : bookList) {
				BookRes res = new BookRes(book.getName(), book.getIsbn(), book.getAuthor(), book.getPrice(),
						book.getSales(), book.getInventory());

				bookFindSeller.add(res);
			}

			seller.setBookResList(bookFindSeller);
			return seller;

			// 判斷輸入的ISBN是否有相符合的資料
		} else if (bookDao.findById(isbnOrNameOrAuthor).isPresent()) {
			Optional<Book> bookOp = bookDao.findById(isbnOrNameOrAuthor);
			BookRes res = new BookRes(bookOp.get().getName(), bookOp.get().getIsbn(), bookOp.get().getAuthor(),
					bookOp.get().getPrice(), bookOp.get().getSales(), bookOp.get().getInventory());
			bookFindSeller.add(res);

			seller.setBookResList(bookFindSeller);
			return seller;

			// 判斷輸入的作者是否為空
		} else if (!bookDao.findByAuthor(isbnOrNameOrAuthor).isEmpty()) {
			List<Book> bookOp = bookDao.findByAuthor(isbnOrNameOrAuthor);
			for (Book book : bookOp) {
				BookRes res = new BookRes(book.getName(), book.getIsbn(), book.getAuthor(), book.getPrice(),
						book.getSales(), book.getInventory());
				bookFindSeller.add(res);
			}

			seller.setBookResList(bookFindSeller);
			return seller;
		}

		// 若以上條件都不符合則回傳書籍不存在的訊息
		return new BookListRes(BookSaleRtnCode.BOOK_ISNOT_EXISTED.getMessage());
	}

	// 更新書籍庫存量(進貨)，顯示書名、ISBN、作者、價格、庫存量
	@Override
	public BookRes updateInventory(String isbn, Integer bookPurchase) {

		// 判斷輸入的ISBN是否存在
		if (!bookDao.findById(isbn).isPresent()) {
			return new BookRes(BookSaleRtnCode.BOOK_ISNOT_EXISTED.getMessage());
		}
		// 判斷輸入的書籍進貨量是否為null或為負值
		if (bookPurchase == null || bookPurchase < 0) {
			return new BookRes(BookSaleRtnCode.BOOK_SALE_REQUIRED.getMessage());
		}

		Book bookUpdate = new Book();
		// 從資料庫取出相符合的書籍資料
		Optional<Book> bookOp = bookDao.findById(isbn);
		bookUpdate = bookOp.get();
		// 將原本的庫存量加上輸入的進貨量
		bookUpdate.setInventory(bookUpdate.getInventory() + bookPurchase);
		// 儲存更新後的書籍資料
		bookDao.save(bookUpdate);

		// 回傳更新後的書籍資料，顯示書名、ISBN、作者、價格及庫存量
		BookRes resUpdate = new BookRes(bookUpdate.getName(), bookUpdate.getIsbn(), bookUpdate.getAuthor(),
				bookUpdate.getPrice(), bookUpdate.getInventory());
		return resUpdate;
	}

	// 更新書籍價格，顯示書名、ISBN、作者、價格、庫存量
	@Override
	public BookRes updatePrice(String isbn, int price) {

		// 判斷輸入的ISBN是否存在
		if (!bookDao.findById(isbn).isPresent()) {
			return new BookRes(BookSaleRtnCode.BOOK_ISNOT_EXISTED.getMessage());
		}
		// 判斷輸入的書籍價格是否為負值
		if (price < 0) {
			return new BookRes(BookSaleRtnCode.BOOK_PRICE_REQUIRED.getMessage());
		}

		// 從資料庫取出相符合的書籍資料
		Book bookPriceUpdate = new Book();
		Optional<Book> bookOp = bookDao.findById(isbn);
		bookPriceUpdate = bookOp.get();
		// 將價格修改成輸入的價格並儲存更新後的資料
		bookPriceUpdate.setPrice(price);
		bookDao.save(bookPriceUpdate);

		// 回傳更新後的書籍資料，顯示書名、ISBN、作者、價格及庫存量
		BookRes resPrUpdate = new BookRes(bookPriceUpdate.getName(), bookPriceUpdate.getIsbn(),
				bookPriceUpdate.getAuthor(), bookPriceUpdate.getPrice(), bookPriceUpdate.getInventory());
		return resPrUpdate;
	}

	// 書籍銷售-消費者購買(可買多本)，顯示書名、ISBN、作者、價格、購買數量、購買總價格
	@Override
	public BookListRes buyBook(BookBuyReq bookBuyList) {
		List<BookRes> bookResList = new ArrayList<>();
		int totalPrice = 0;
		BookListRes bookSaleList = new BookListRes();

		// 將輸入的書籍(多本)List foreach，取得ISBN及購買數量
		for (BookBuyReq buyReq : bookBuyList.getBookBuyList()) {
			// 判斷輸入的ISBN是否存在
			if (!bookDao.findById(buyReq.getIsbn()).isPresent()) {
				return new BookListRes(BookSaleRtnCode.BOOK_ISNOT_EXISTED.getMessage());
			}
			// 判斷輸入的購買數量是否為null或是負值
			if (buyReq.getQuantity() == null || buyReq.getQuantity() < 0) {
				return new BookListRes(BookSaleRtnCode.INPUT_ERROR.getMessage());
			}

			// 找出相符合ISBN的書籍資料
			Book buyBook = new Book();
			Optional<Book> bookOp = bookDao.findById(buyReq.getIsbn());
			buyBook = bookOp.get();
			// 將書籍的銷量加上購買數量
			buyBook.setSales(buyBook.getSales() + buyReq.getQuantity());
			// 將書籍的庫存量扣除購買數量
			buyBook.setInventory(buyBook.getInventory() - buyReq.getQuantity());
			bookDao.save(buyBook);

			// 需要顯示的書名、ISBN、作者、價格、購買數量、購買總價格存入List中
			BookRes resBuyBook = new BookRes(buyBook.getName(), buyBook.getPrice(), buyBook.getIsbn(),
					buyBook.getAuthor(), buyReq.getQuantity(), buyBook.getPrice() * buyReq.getQuantity());
			bookResList.add(resBuyBook);

			// 購買總價格為書籍價格乘上購買數量
			totalPrice += buyBook.getPrice() * buyReq.getQuantity();
		}

		// 回傳要顯示的書籍資料以及購買總價格
		bookSaleList.setBookResList(bookResList);
		bookSaleList.setTotalPrice(totalPrice);
		return bookSaleList;
	}

	// 暢銷書排行榜(銷售量前5)，顯示書名、ISBN、作者、價格
	@Override
	public List<Book> bookTop5() {

		// 從資料庫找出銷售量前五由大到小排列
		List<Book> findbooktop5 = new ArrayList<>();
		findbooktop5 = bookDao.findTop5ByOrderBySalesDesc();
		return findbooktop5;
	}

}
