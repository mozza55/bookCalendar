package com.bookcalendar.demo.service;

import com.bookcalendar.demo.repository.BookScoreDto;
import com.bookcalendar.demo.repository.InventoryBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BestbookService {
    private final InventoryBookRepository inventoryBookRepository;

    //inventorybook을 조회해서 베스트 책을 선별한다
    public Long createBestbookList(){
        return -1L;
    }


    public List<BookScoreDto> getBestBookList(LocalDate localDate) {
        return inventoryBookRepository.getBookAddScoreOn(localDate);
    }
}
