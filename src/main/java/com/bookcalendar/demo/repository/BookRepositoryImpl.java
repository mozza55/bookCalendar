package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.domain.QBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

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

    @Override
    public Page<Book> findAllByBookSearch(BookSearch bookSearch, Pageable pageable) {
        QBook book = QBook.book;
        String field = bookSearch.getSearchField();
        String word = bookSearch.getSearchWord();
        BooleanBuilder builder = new BooleanBuilder();
        if(field.equals("all")){
            builder.and(book.title.containsIgnoreCase(word));
            builder.or(book.author.containsIgnoreCase(word));
            builder.or(book.publisher.containsIgnoreCase(word));
            builder.or(book.isbn.containsIgnoreCase(word));
        }else if(field.equals("title")){
            builder.and(book.title.containsIgnoreCase(word));
        }else if(field.equals("author")){
            builder.and(book.author.containsIgnoreCase(word));
        }else if(field.equals("publisher")){
            builder.and(book.publisher.containsIgnoreCase(word));
        }else{
            builder.and(book.isbn.containsIgnoreCase(word));
        }
        JPQLQuery<Book> query = from(book)
                .where(builder)
                .distinct();
        Long totalCount = query.fetchCount();
        List<Book> results = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(results,pageable,totalCount);
    }
}
