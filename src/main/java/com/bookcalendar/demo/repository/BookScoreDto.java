package com.bookcalendar.demo.repository;

import lombok.Data;

@Data
public class BookScoreDto {
    private Long bookid;
    private Long score;

    public BookScoreDto(Long bookid, Long score) {
        this.bookid = bookid;
        this.score = score;
    }
}
