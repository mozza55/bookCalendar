package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.InventoryBook;
import com.bookcalendar.demo.domain.QInventoryBook;
import com.bookcalendar.demo.dto.InventoryBookWithBookDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.sql.SQLExpressions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    @Override
    public List<InventoryBookWithBookDto> getDtosByInventoryId(Long inventoryId) {
        QInventoryBook inventoryBook = QInventoryBook.inventoryBook;
        List<InventoryBookWithBookDto> inventoryBookWithBookDtoList = from(inventoryBook).select(Projections.constructor(InventoryBookWithBookDto.class,
                inventoryBook, inventoryBook.book))
                .where(inventoryBook.inventory.id.eq(inventoryId))
                .fetch();
        return inventoryBookWithBookDtoList;
    }

    @Override
    public Page<InventoryBookWithBookDto> getDtosByInventoryId(Long inventoryId, Pageable pageable) {
        QInventoryBook inventoryBook = QInventoryBook.inventoryBook;
        JPQLQuery<InventoryBookWithBookDto> query = from(inventoryBook).select(Projections.constructor(InventoryBookWithBookDto.class,
                inventoryBook, inventoryBook.book))
                .where(inventoryBook.inventory.id.eq(inventoryId))
                .distinct();

        Long totalCount = query.fetchCount();
        List<InventoryBookWithBookDto> results = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(results,pageable,totalCount);
    }
}
