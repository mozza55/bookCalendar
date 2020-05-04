package com.bookcalendar.demo.domain;

import com.bookcalendar.demo.exception.NotEnoughCountException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Book {
    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String title;

    private String author;

    private String publisher;

    private String isbn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(mappedBy ="book")
    private List<InventoryBook> inventoryBookList = new ArrayList<>();

    private double rating;

    private int page;

    private int readCount;
    private int addCount;


    public static Book createBook(String title, String author, String publisher, String isbn, int page){
        Book book = new Book();
        book.title=title;
        book.author=author;
        book.publisher=publisher;
        book.isbn=isbn;
        book.page=page;
        book.readCount=0;
        book.addCount=0;
        book.rating =0;
        return book;
    }
    //비지니스 로직
    public void addReadCount(){ this.readCount++; }
    public void addAddCount(){ this.addCount++; }
    public void removeReadCount(){
        if(this.readCount-1 <0 ){
            throw new NotEnoughCountException("읽은 독자 없음");
        }
        this.readCount--;
    }
    public void removeAddCount(){
        if(this.addCount-1 <0 ){
            throw new NotEnoughCountException("읽은 독자 없음");
        }
        this.addCount--;
    }
}
