package com.ahnis.repository;

import com.ahnis.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book,Integer> {

@Override
    List<Book> findAll();


}
