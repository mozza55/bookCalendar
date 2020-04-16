package com.bookcalendar.demo.repository;



import java.time.LocalDate;
import java.util.List;

public interface InventoryBookRepositoryCustom {

    List<BookScoreDto> getBookAddScoreOn(LocalDate from, LocalDate to);
    List<BookScoreDto> getBookReadScoreOn(LocalDate from, LocalDate to);
}
