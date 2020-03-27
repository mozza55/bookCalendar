package com.bookcalendar.demo.domain;

import com.bookcalendar.demo.exception.NotEnoughCountException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Book {
    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String title;

    private String author;

    private String isbn;

    private double rating;

    private int page;

    private int readCount;

    public static Book createBook(String title, String author, String isbn, int page){
        Book book = new Book();
        book.title=title;
        book.author=author;
        book.isbn=isbn;
        book.page=page;
        book.readCount=0;
        book.rating =0;
        return book;
    }
    //비지니스 로직
    public void addReadCount(){ this.readCount++; }
    public void removeReadCount(){
        if(this.readCount-1 <0 ){
            throw new NotEnoughCountException("읽은 독자 없음");
        }
        this.readCount--;
    }
}
