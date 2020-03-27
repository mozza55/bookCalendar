package com.bookcalendar.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="inventory_book")
@Getter @Setter
public class InventoryBook {
    @Id @GeneratedValue
    @Column(name = "inventory_book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Book book;

    @Enumerated(value = EnumType.STRING)
    private ReadStatus status;

    private int currentPage;
    private int page;

    private String comment;

    //생성 메서드
    public static InventoryBook createInventoryBook(Book book){
        InventoryBook inventoryBook = new InventoryBook();
        inventoryBook.setBook(book);
        inventoryBook.setPage(book.getPage());
        inventoryBook.setCurrentPage(0);
        inventoryBook.setStatus(ReadStatus.TODO);
        return  inventoryBook;
    }

    // 다 읽었을 때
    public void finishInventoryBook(){
        setCurrentPage(getPage());
        setStatus(ReadStatus.DONE);
    }



}
