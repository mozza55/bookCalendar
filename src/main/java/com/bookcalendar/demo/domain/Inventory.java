package com.bookcalendar.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Inventory {
    @Id @GeneratedValue
    @Column(name = "inventory_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "inventory",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryBook> inventoryBooks = new ArrayList<>();

    private int bookCount;

    public Inventory() {
        bookCount =0;
    }

    //연관관계 편의 메서드
    public void addBook(InventoryBook inventoryBook){
        this.inventoryBooks.add(inventoryBook);
        inventoryBook.setInventory(this);
        bookCount++;
    }

    public void removeBook(){
        bookCount--;
    }
    //전체 페이지 수 조회
    public int getTotalPage(){
        int totalPage = 0;
        for(InventoryBook inventoryBook: inventoryBooks ){
            totalPage += inventoryBook.getCurrentPage();
        }
        return totalPage;
    }
}