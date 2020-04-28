package com.bookcalendar.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter @Setter
public class Event {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="calendar_id")
    private Calendar calendar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_book_id")
    private InventoryBook inventoryBook;
    private LocalDate date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private int startPage;
    private int endPage;
    private String comment;

    public static Event createEvent(Calendar c, InventoryBook ib, LocalDate date,LocalDateTime startTime, LocalDateTime endTime ){
        Event e = new Event();
        e.calendar = c;
        e.inventoryBook = ib;
        e.date = date;
        e.startTime = startTime;
        e.endTime = endTime;
        return e;
    }
    public static Event createEvent(Calendar c, InventoryBook ib, LocalDate date,LocalDateTime startTime, LocalDateTime endTime, int startPage, int endPage ){
        Event e = new Event();
        e.calendar = c;
        e.inventoryBook = ib;
        e.date = date;
        e.startTime = startTime;
        e.endTime = endTime;
        e.startPage = startPage;
        e.endPage = endPage;
        return e;
    }
}
