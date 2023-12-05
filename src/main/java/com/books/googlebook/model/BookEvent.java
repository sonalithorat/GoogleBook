package com.books.googlebook.model;

import com.books.googlebook.entity.Books;

public record BookEvent(

		Integer BookEventId,

		Books books) {

}
