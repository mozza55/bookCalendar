package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>, BookRepositoryCustom {
    @Query("select b from Book b " +
            " where b.title like %?1% " +
            " or b.author like %?1% " +
            " or b.isbn like %?1% ")
    Page<Book> getSearchList(String search, Pageable pageable);

}
