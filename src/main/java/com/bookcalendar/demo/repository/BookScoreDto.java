package com.bookcalendar.demo.repository;

import lombok.Data;

@Data
public class BookScoreDto implements Comparable<BookScoreDto> {
    private Long bookid;
    private int score;
    private int rank;

    public BookScoreDto(Long bookid, int score) {
        this.bookid = bookid;
        this.score = score;
    }
    public BookScoreDto(Long bookid, Long score) {
        this.bookid = bookid;
        this.score = score.intValue();
    }

    @Override
    public int compareTo(BookScoreDto o) {
        int targetScore = o.getScore();
        if(score ==targetScore) return 0;
        else if(score>targetScore) return 1;
        else return -1;
    }
}
