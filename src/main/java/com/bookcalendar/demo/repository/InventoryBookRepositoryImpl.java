package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.domain.InventoryBook;
import com.bookcalendar.demo.domain.QInventoryBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.sql.SQLExpressions;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.util.List;

public class InventoryBookRepositoryImpl
    extends QuerydslRepositorySupport implements InventoryBookRepositoryCustom {

    public InventoryBookRepositoryImpl() {
        super(InventoryBook.class);
    }

    @Override
    public List<BookScoreDto> getBookAddScoreOn(LocalDate from, LocalDate to) {
        QInventoryBook inventoryBook = QInventoryBook.inventoryBook;
        List<BookScoreDto> bookScoreDtos = from(inventoryBook).select(Projections.constructor(BookScoreDto.class,
                inventoryBook.book.id, SQLExpressions.count(inventoryBook.book.id)))
                .where(inventoryBook.addDate.between(from,to))
                .groupBy(inventoryBook.book.id)
                .fetch();

        return bookScoreDtos;
    }

    @Override
    public List<BookScoreDto> getBookReadScoreOn(LocalDate from, LocalDate to) {
        QInventoryBook inventoryBook = QInventoryBook.inventoryBook;
        List<BookScoreDto> bookScoreDtos = from(inventoryBook).select(Projections.constructor(BookScoreDto.class,
                inventoryBook.book.id, SQLExpressions.count(inventoryBook.book.id)))
                .where(inventoryBook.readDate.between(from,to))
                .groupBy(inventoryBook.book.id)
                .fetch();

        return bookScoreDtos;
    }
}
