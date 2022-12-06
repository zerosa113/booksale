package com.example.booksale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.booksale.repository.BookDao;
import com.example.booksale.service.ifs.BookSaleService;

@SpringBootTest(classes = BookSaleApplication.class)	
class BooksaleApplicationTests {
	
	@Autowired
	private BookSaleService booksaleService;
	
	@Autowired
	private BookDao bookDao;

	@Test
	void contextLoads() {
	}

}
