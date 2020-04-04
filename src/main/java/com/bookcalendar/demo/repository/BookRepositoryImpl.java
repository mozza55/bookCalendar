package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.domain.QBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

//@NoRepositoryBean //있으면 안됨
public class BookRepositoryImpl
    extends QuerydslRepositorySupport implements BookRepositoryCustom {


    public BookRepositoryImpl() {
        super(Book.class);
    }

    @Override
    public Book findAllBySearch(String title,String author) {
        QBook book = QBook.book;
        return from(book)
                .where(titleLike(title),
                        authorLike(author))
                .select(book)
                .fetchOne();
    }

    private BooleanExpression titleLike(String title){
        if(StringUtils.isEmpty(title)) return null;
        return QBook.book.title.containsIgnoreCase(title);
    }

    private BooleanExpression authorLike(String author){
        if(StringUtils.isEmpty(author)) return null;
        return QBook.book.title.containsIgnoreCase(author);
    }
}
