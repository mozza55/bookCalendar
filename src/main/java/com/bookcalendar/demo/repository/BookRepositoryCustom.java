package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRepositoryCustom {
    Book findAllBySearch(String title, String author);

    Page<Book> findAllByBookSearch(BookSearch bookSearch, Pageable pageable);
}
