package com.bookcalendar.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Bestbook {
    @Id @GeneratedValue
    @Column(name = "Bestbook_id")
    private Long id;

    private LocalDate period;
    private int rank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private int score;

    public Bestbook() {
    }

    public Bestbook(LocalDate period, int rank, Book book, int score ) {
        this.period = period;
        this.rank = rank;
        this.score = score;
        this.book = book;
    }
}
