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

	// �إ߮��y���
	@Override
	public BookRes createBook(BookReq req) {

		// ���bcheck params
		BookRes res = new BookRes();
		res = checkParams(req);
		if (res != null) {
			return res;
		}

		Book book = new Book(req.getIsbn(), req.getName(), req.getAuthor(), req.getPrice(), req.getInventory(),
				req.getSales(), req.getGenre());
		// �T�{�D�䤣�Onull
		bookDao.existsById(req.getIsbn());
		// �N�إߪ���Ʀs�J��Ʈw
		bookDao.save(book);
		// �]�w�ϥΪ̿�J�����
		BookRes newRes = new BookRes(req.getName(), req.getIsbn(), req.getAuthor(), req.getPrice(), req.getSales(),
				req.getInventory(), req.getGenre());
		// �^����ܵ��ϥΪ̬ݿ�J�����
		return newRes;
	}

	private BookRes checkParams(BookReq req) {
		// �T�{ISBN�O�_������
		if (bookDao.findById(req.getIsbn()).isPresent()) {
			return new BookRes(BookSaleRtnCode.ISBN_EXISTED.getMessage());
		}
		// �T�{��J���r��O�_��null
		if (!StringUtils.hasText(req.getIsbn())) {
			// �^��message���isbn��J���~
			BookRes res = new BookRes(BookSaleRtnCode.BOOK_ISBN_REQUIRED.getMessage());
			return res;
		}
		if (!StringUtils.hasText(req.getName())) {
			// �^��message��ܮѦW��J���~
			BookRes res = new BookRes(BookSaleRtnCode.BOOK_NAME_REQUIRED.getMessage());
			return res;
		}
		if (!StringUtils.hasText(req.getAuthor())) {
			// �^��message��ܧ@�̿�J���~
			BookRes res = new BookRes(BookSaleRtnCode.BOOK_AUTHOR_REQUIRED.getMessage());
			return res;
		}
		// �]�w���y���椣�o���t
		if (req.getPrice() < 0) {
			// �^��message��ܻ����J���~
			BookRes res = new BookRes(BookSaleRtnCode.BOOK_PRICE_REQUIRED.getMessage());
			return res;
		}
		if (!StringUtils.hasText(req.getGenre())) {
			// �^��message������O��J���~
			BookRes res = new BookRes(BookSaleRtnCode.BOOK_GENRE_REQUIRED.getMessage());
			return res;
		}

		return null;
	}

	// �z�L���y�����j�M���y���
	@Override
	public BookRes bookGenreSearch(String genre) {
		// ���b�T�{�O�_��J���T
		if (!StringUtils.hasText(genre)) {
			return new BookRes(BookSaleRtnCode.BOOK_ISNOT_EXISTED.getMessage());
		}

		// �γr�����ο�J�������r��
		String[] bookArray = genre.split(",");
		// �إ�Set�ΨӦs����y����
		Set<String> genreSet = new HashSet<>();
		for (String book : bookArray) {
			// �h���Ů�
			String str = book.trim();
			// ��JSet�|�⭫�ƪ��r���л\��
			genreSet.add(str);
		}

		// �q��Ʈw����X�Ҧ���Ʀs��iList
		List<Book> bookList = bookDao.findAll();
		// �إ�Set�ΨӦs����y
		Set<Book> bookSet = new HashSet<>();

		// �N���y�����O�P��Ʈw���Ҧ���ư����
		for (String genreStr : genreSet) {
			for (Book book : bookList) {
				// �]�w����]�t
				if (book.getGenre().contains(genreStr)) {
					// ���Ʈw�t��genre����r���ѥ[�iSet��
					bookSet.add(book);
				}
			}
		}

		// �^�Ǧs�bSet�����y(�h��)
		BookRes res = new BookRes();
		res.setBookSet(bookSet);
		return res;
	}

	// ���O�̳z�L���y�W�١BISBN�Χ@�̷j�M
	@Override
	public BookListRes bookSearchConsumer(String isbnOrNameOrAuthor) {
		List<BookRes> bookResList = new ArrayList<>();
		BookListRes consumer = new BookListRes();

		// �P�_��J���ѦW�O�_����
		if (!bookDao.findByName(isbnOrNameOrAuthor).isEmpty()) {
			// �ϥ�findByName�M��۲ŦX�����
			List<Book> bookList = bookDao.findByName(isbnOrNameOrAuthor);
			// foreach bookList �N�a����ƪ����y�ӧO��X�s�JbookResList��
			for (Book book : bookList) {
				BookRes bookRes = new BookRes(book.getName(), book.getIsbn(), book.getAuthor(), book.getPrice());
				bookResList.add(bookRes);
			}

			consumer.setBookResList(bookResList);
			return consumer;

			// �P�_��J��ISBN�O�_���۲ŦX�����
		} else if (bookDao.findById(isbnOrNameOrAuthor).isPresent()) {
			Optional<Book> bookId = bookDao.findById(isbnOrNameOrAuthor);
			BookRes bookRes = new BookRes(bookId.get().getName(), bookId.get().getIsbn(), bookId.get().getAuthor(),
					bookId.get().getPrice());
			bookResList.add(bookRes);

			consumer.setBookResList(bookResList);
			return consumer;

			// �P�_��J���@�̬O�_����
		} else if (!bookDao.findByAuthor(isbnOrNameOrAuthor).isEmpty()) {
			List<Book> bookList = bookDao.findByAuthor(isbnOrNameOrAuthor);
			for (Book book : bookList) {
				BookRes bookRes = new BookRes(book.getName(), book.getIsbn(), book.getAuthor(), book.getPrice());
				bookResList.add(bookRes);
			}

			consumer.setBookResList(bookResList);
			return consumer;
		}

		// �Y�H�W���󳣤��ŦX�h�^�Ǯ��y���s�b���T��
		return new BookListRes(BookSaleRtnCode.BOOK_ISNOT_EXISTED.getMessage());
	}

	// ���y�ӳz�L���y�W�١BISBN�Χ@�̷j�M
	@Override
	public BookListRes bookSearchSeller(String isbnOrNameOrAuthor) {
		List<BookRes> bookFindSeller = new ArrayList<>();
		BookListRes seller = new BookListRes();

		// �P�_��J���ѦW�O�_����
		if (!bookDao.findByName(isbnOrNameOrAuthor).isEmpty()) {
			List<Book> bookList = bookDao.findByName(isbnOrNameOrAuthor);
			for (Book book : bookList) {
				BookRes res = new BookRes(book.getName(), book.getIsbn(), book.getAuthor(), book.getPrice(),
						book.getSales(), book.getInventory());

				bookFindSeller.add(res);
			}

			seller.setBookResList(bookFindSeller);
			return seller;

			// �P�_��J��ISBN�O�_���۲ŦX�����
		} else if (bookDao.findById(isbnOrNameOrAuthor).isPresent()) {
			Optional<Book> bookOp = bookDao.findById(isbnOrNameOrAuthor);
			BookRes res = new BookRes(bookOp.get().getName(), bookOp.get().getIsbn(), bookOp.get().getAuthor(),
					bookOp.get().getPrice(), bookOp.get().getSales(), bookOp.get().getInventory());
			bookFindSeller.add(res);

			seller.setBookResList(bookFindSeller);
			return seller;

			// �P�_��J���@�̬O�_����
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

		// �Y�H�W���󳣤��ŦX�h�^�Ǯ��y���s�b���T��
		return new BookListRes(BookSaleRtnCode.BOOK_ISNOT_EXISTED.getMessage());
	}

	// ��s���y�w�s�q(�i�f)�A��ܮѦW�BISBN�B�@�̡B����B�w�s�q
	@Override
	public BookRes updateInventory(String isbn, Integer bookPurchase) {

		// �P�_��J��ISBN�O�_�s�b
		if (!bookDao.findById(isbn).isPresent()) {
			return new BookRes(BookSaleRtnCode.BOOK_ISNOT_EXISTED.getMessage());
		}
		// �P�_��J�����y�i�f�q�O�_��null�ά��t��
		if (bookPurchase == null || bookPurchase < 0) {
			return new BookRes(BookSaleRtnCode.BOOK_SALE_REQUIRED.getMessage());
		}

		Book bookUpdate = new Book();
		// �q��Ʈw���X�۲ŦX�����y���
		Optional<Book> bookOp = bookDao.findById(isbn);
		bookUpdate = bookOp.get();
		// �N�쥻���w�s�q�[�W��J���i�f�q
		bookUpdate.setInventory(bookUpdate.getInventory() + bookPurchase);
		// �x�s��s�᪺���y���
		bookDao.save(bookUpdate);

		// �^�ǧ�s�᪺���y��ơA��ܮѦW�BISBN�B�@�̡B����ήw�s�q
		BookRes resUpdate = new BookRes(bookUpdate.getName(), bookUpdate.getIsbn(), bookUpdate.getAuthor(),
				bookUpdate.getPrice(), bookUpdate.getInventory());
		return resUpdate;
	}

	// ��s���y����A��ܮѦW�BISBN�B�@�̡B����B�w�s�q
	@Override
	public BookRes updatePrice(String isbn, int price) {

		// �P�_��J��ISBN�O�_�s�b
		if (!bookDao.findById(isbn).isPresent()) {
			return new BookRes(BookSaleRtnCode.BOOK_ISNOT_EXISTED.getMessage());
		}
		// �P�_��J�����y����O�_���t��
		if (price < 0) {
			return new BookRes(BookSaleRtnCode.BOOK_PRICE_REQUIRED.getMessage());
		}

		// �q��Ʈw���X�۲ŦX�����y���
		Book bookPriceUpdate = new Book();
		Optional<Book> bookOp = bookDao.findById(isbn);
		bookPriceUpdate = bookOp.get();
		// �N����ק令��J��������x�s��s�᪺���
		bookPriceUpdate.setPrice(price);
		bookDao.save(bookPriceUpdate);

		// �^�ǧ�s�᪺���y��ơA��ܮѦW�BISBN�B�@�̡B����ήw�s�q
		BookRes resPrUpdate = new BookRes(bookPriceUpdate.getName(), bookPriceUpdate.getIsbn(),
				bookPriceUpdate.getAuthor(), bookPriceUpdate.getPrice(), bookPriceUpdate.getInventory());
		return resPrUpdate;
	}

	// ���y�P��-���O���ʶR(�i�R�h��)�A��ܮѦW�BISBN�B�@�̡B����B�ʶR�ƶq�B�ʶR�`����
	@Override
	public BookListRes buyBook(BookBuyReq bookBuyList) {
		List<BookRes> bookResList = new ArrayList<>();
		int totalPrice = 0;
		BookListRes bookSaleList = new BookListRes();

		// �N��J�����y(�h��)List foreach�A���oISBN���ʶR�ƶq
		for (BookBuyReq buyReq : bookBuyList.getBookBuyList()) {
			// �P�_��J��ISBN�O�_�s�b
			if (!bookDao.findById(buyReq.getIsbn()).isPresent()) {
				return new BookListRes(BookSaleRtnCode.BOOK_ISNOT_EXISTED.getMessage());
			}
			// �P�_��J���ʶR�ƶq�O�_��null�άO�t��
			if (buyReq.getQuantity() == null || buyReq.getQuantity() < 0) {
				return new BookListRes(BookSaleRtnCode.INPUT_ERROR.getMessage());
			}

			// ��X�۲ŦXISBN�����y���
			Book buyBook = new Book();
			Optional<Book> bookOp = bookDao.findById(buyReq.getIsbn());
			buyBook = bookOp.get();
			// �N���y���P�q�[�W�ʶR�ƶq
			buyBook.setSales(buyBook.getSales() + buyReq.getQuantity());
			// �N���y���w�s�q�����ʶR�ƶq
			buyBook.setInventory(buyBook.getInventory() - buyReq.getQuantity());
			bookDao.save(buyBook);

			// �ݭn��ܪ��ѦW�BISBN�B�@�̡B����B�ʶR�ƶq�B�ʶR�`����s�JList��
			BookRes resBuyBook = new BookRes(buyBook.getName(), buyBook.getPrice(), buyBook.getIsbn(),
					buyBook.getAuthor(), buyReq.getQuantity(), buyBook.getPrice() * buyReq.getQuantity());
			bookResList.add(resBuyBook);

			// �ʶR�`���欰���y���歼�W�ʶR�ƶq
			totalPrice += buyBook.getPrice() * buyReq.getQuantity();
		}

		// �^�ǭn��ܪ����y��ƥH���ʶR�`����
		bookSaleList.setBookResList(bookResList);
		bookSaleList.setTotalPrice(totalPrice);
		return bookSaleList;
	}

	// �Z�P�ѱƦ�](�P��q�e5)�A��ܮѦW�BISBN�B�@�̡B����
	@Override
	public List<Book> bookTop5() {

		// �q��Ʈw��X�P��q�e���Ѥj��p�ƦC
		List<Book> findbooktop5 = new ArrayList<>();
		findbooktop5 = bookDao.findTop5ByOrderBySalesDesc();
		return findbooktop5;
	}

}
