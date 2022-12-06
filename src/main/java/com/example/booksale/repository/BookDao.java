package com.example.booksale.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.booksale.entity.Book;

@Repository
public interface BookDao extends JpaRepository<Book, String> {
	
	// Containing找到類別裡，所有有包含輸入字串的書，findByGenre只單獨找一個類別
	public List<Book> findByGenreContaining(String genre);

	public List<Book> findByInventory(String isbn);

	public List<Book> findByName(String name);

	public List<Book> findByAuthor(String author);

	public List<Book> findTop5ByOrderBySalesDesc();

}
