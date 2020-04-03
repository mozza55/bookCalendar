package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Book;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository

public interface BookRepository extends JpaRepository<Book,Long>,
        QuerydslPredicateExecutor<Book> {



    @Query("select b from Book b " +
            " where b.title like %?1% " +
            " or b.author like %?1% " +
            " or b.isbn like %?1% ")
    Page<Book> getSearchList(String search, Pageable pageable);


}
