package com.example.booksale.constants;

public enum BookSaleRtnCode {
	
	SUCCESSFUL("200","Successful"),
	ISBN_REQUIRED("400","ISBN is required!"),
	ISBN_EXISTED("403","ISBN has existed!"),

	BOOK_REQUIRED("400","Book is required!"),
	BOOK_ISBN_REQUIRED("400","BookISBN is required!"),
	BOOK_NAME_REQUIRED("400","Book name is required!"),
	BOOK_AUTHOR_REQUIRED("400","Book author is required!"),
	BOOK_PRICE_REQUIRED("400","Book price is required!"),
	BOOK_GENRE_REQUIRED("400","Book genre is required!"),
	INPUT_ERROR("400","Input error!"),
	BOOK_SALE_REQUIRED("400","Booksale is required!"),
	BOOK_ISNOT_EXISTED("400","Book isnot existed!"),
	
	ADD_GENRE_FAILURE("400","Add genre failure!"),
	GENRE_LIST_IS_EMPTY("400","Genre list is empty");
	
	private String code;
	
	private String message;
	
	private BookSaleRtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
