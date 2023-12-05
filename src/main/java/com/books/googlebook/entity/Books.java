package com.books.googlebook.entity;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Books")

public class Books {

	@Id
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

	@Override
	public String toString() {
		return "Books [id=" + id + ", name=" + name + ", description=" + description + ", author=" + author + ", price="
				+ price + ", category=" + category + ", pageCount=" + pageCount + ", viewDate=" + viewDate + ", stock="
				+ stock + ", about=" + about + ", publisher=" + publisher + ", isBorrowed=" + isBorrowed + "]";
	}

}
