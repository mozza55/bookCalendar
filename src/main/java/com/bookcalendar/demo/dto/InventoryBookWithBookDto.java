package com.bookcalendar.demo.dto;

import com.bookcalendar.demo.domain.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor
public class InventoryBookWithBookDto {
    private Long inventoryBookId;
    private ReadStatus status;
    private int currentPage;
    private LocalDate addDate;
    private LocalDate readDate;
    private String comment;

    private Long bookId;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private double rating;
    private int page;
    private int readCount;
    private int addCount;

    public InventoryBookWithBookDto(InventoryBook inventoryBook, Book book){
        this.inventoryBookId = inventoryBook.getId();
        this.status = inventoryBook.getStatus();
        this.currentPage = inventoryBook.getCurrentPage();
        this.addDate = inventoryBook.getAddDate();
        this.readDate = inventoryBook.getReadDate();
        this.comment = inventoryBook.getComment();
        this.bookId = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.isbn = book.getIsbn();
        this.rating = book.getRating();
        this.page = book.getPage();
        this.readCount = book.getReadCount();
        this.addCount = book.getAddCount();
    }
}
