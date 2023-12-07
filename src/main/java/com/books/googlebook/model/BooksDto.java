package com.books.googlebook.model;

import java.time.LocalDate;

public class BooksDto {

	int id;
	String name;
	String description;
	String author;
	int price;
	String category;
	int pageCount;
	LocalDate viewDate;
	int stock;
	String about;
	String publisher;
	boolean isBorrowed;

	boolean addedToCart;

	public boolean isAddedToCart() {
		return addedToCart;
	}

	public void setAddedToCart(boolean addedToCart) {
		this.addedToCart = addedToCart;
	}

	public BooksDto() {
		super();
	}

	public BooksDto(int id, String name, String description, String author, int price, String category, int pageCount,
			int stock, String about, String publisher, boolean isBorrowed, boolean addedToCart) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.author = author;
		this.price = price;
		this.category = category;
		this.pageCount = pageCount;
		this.viewDate = viewDate;
		this.stock = stock;
		this.about = about;
		this.publisher = publisher;
		this.isBorrowed = isBorrowed;
		this.addedToCart = addedToCart;
	}

	public boolean isBorrowed() {
		return isBorrowed;
	}

	public void setBorrowed(boolean isBorrowed) {
		this.isBorrowed = isBorrowed;
	}

	public LocalDate getViewDate() {
		return viewDate;
	}

	public void setViewDate(LocalDate viewDate) {
		this.viewDate = viewDate;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
