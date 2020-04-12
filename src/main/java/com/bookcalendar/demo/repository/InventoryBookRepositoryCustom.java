package com.bookcalendar.demo.repository;



import java.time.LocalDate;
import java.util.List;

public interface InventoryBookRepositoryCustom {

    List<BookScoreDto> getBookAddScoreOn(LocalDate localDate);
    List<BookScoreDto> getBookReadScoreOn(LocalDate localDate);
}
