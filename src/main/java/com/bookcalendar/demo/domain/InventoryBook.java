package com.bookcalendar.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "inventoryBook",cascade = CascadeType.REMOVE)
    private List<Event> eventList = new ArrayList<>();

    private int currentPage;
    private int page;
    private LocalDate addDate;
    private LocalDate readDate;
    private int rating;
    private String comment;

    //생성 메서드
    public static InventoryBook createInventoryBook(Book book){
        InventoryBook inventoryBook = new InventoryBook();
        inventoryBook.setBook(book);
        inventoryBook.setPage(book.getPage());
        inventoryBook.setCurrentPage(0);
        inventoryBook.setStatus(ReadStatus.TODO);
        inventoryBook.setAddDate(LocalDate.now());
        inventoryBook.setRating(0);
        book.addAddCount();
        return  inventoryBook;
    }
    public static InventoryBook createInventoryBook(Book book,Inventory inventory){
        InventoryBook inventoryBook = new InventoryBook();
        inventoryBook.setPage(book.getPage());
        inventoryBook.setCurrentPage(0);
        inventoryBook.setStatus(ReadStatus.TODO);
        inventoryBook.setAddDate(LocalDate.now());
        inventoryBook.setRating(0);
        //book 연관관계
        inventoryBook.setBook(book);
        book.getInventoryBookList().add(inventoryBook);
        book.addAddCount();
        //inventory 연관관계
        inventoryBook.setInventory(inventory);
        inventory.getInventoryBooks().add(inventoryBook);
        inventory.addAddCount();
        return  inventoryBook;
    }

    // 다 읽었을 때
    public void finishInventoryBook(){
        this.setCurrentPage(this.getPage());
        this.setStatus(ReadStatus.DONE);
        this.setReadDate(LocalDate.now());
        this.book.removeAddCount();
        this.book.addReadCount();
        this.inventory.removeAddCount();
        this.inventory.addReadCount();
    }

    public void removeInventoryBook(){
        if(this.status ==ReadStatus.DONE){
            this.book.removeReadCount();
            this.inventory.removeReadCount();
        }else{
            this.book.removeAddCount();
            this.inventory.removeAddCount();
        }
    }

}
