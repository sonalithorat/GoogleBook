package com.books.googlebook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.books.googlebook.entity.Books;


public interface BooksRepository extends MongoRepository<Books, Integer>{
	
	@Query("{$or :[{author: ?0},{name: ?1}]}")
    Optional<List<Books>> getBookByName(String author, String name);
	
	
	
	@Query("{$or :[{author: {$regex : ?0 }},{name:{$regex: ?0}},{description :{$regex :?0}}]}")
	Optional<List<Books>> findByAuthorContainingOrByNameContainingOrByDescriptionContaining(String name);
}


//{$or :[{ "author" : { "$regex" : "science"}},{ "name" : { "$regex" : "science"}},{ "description" : { "$regex" : "science"}}]}