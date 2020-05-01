package com.bookcalendar.demo.repository;



import com.bookcalendar.demo.dto.InventoryBookWithBookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface InventoryBookRepositoryCustom {

    List<BookScoreDto> getBookAddScoreOn(LocalDate from, LocalDate to);
    List<BookScoreDto> getBookReadScoreOn(LocalDate from, LocalDate to);

    List<InventoryBookWithBookDto> getDtosByInventoryId(Long inventoryId);
    Page<InventoryBookWithBookDto> getDtosByInventoryId(Long inventoryId, Pageable pageable);
}
