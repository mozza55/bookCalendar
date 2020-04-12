package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Bestbook;
import com.bookcalendar.demo.domain.Book;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookRepositoryCustom {
    Book findAllBySearch(String title, String author);

    Page<Book> findAllByBookSearch(BookSearch bookSearch, Pageable pageable);


}
