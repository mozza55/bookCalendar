package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Book;

import java.util.List;

public interface BookRepositoryCustom {
    Book findAllBySearch(String title, String author);
}
