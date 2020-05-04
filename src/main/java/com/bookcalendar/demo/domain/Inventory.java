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

    @OneToMany(mappedBy = "inventory")
    private List<InventoryBook> inventoryBooks = new ArrayList<>();

    private int addCount;
    private int readCount;
    private int pagePerDay;
    private int bookPerDay;

    public Inventory() {
        bookPerDay=0;
        pagePerDay=0;
        addCount =0;
        readCount=0;
    }

    //연관관계 편의 메서드
    public void addBook(InventoryBook inventoryBook){
        this.inventoryBooks.add(inventoryBook);
        inventoryBook.setInventory(this);
        this.addAddCount();
    }

    public void addAddCount(){
        addCount++;
    }
    public void addReadCount(){
        readCount++;
    }
    public void removeAddCount(){
        addCount--;
    }
    public void removeReadCount(){
        readCount--;
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
