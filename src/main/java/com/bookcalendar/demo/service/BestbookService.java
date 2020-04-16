package com.bookcalendar.demo.service;

import com.bookcalendar.demo.domain.Bestbook;
import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.repository.BestbookRepository;
import com.bookcalendar.demo.repository.BookRepository;
import com.bookcalendar.demo.repository.BookScoreDto;
import com.bookcalendar.demo.repository.InventoryBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BestbookService {
    private final InventoryBookRepository inventoryBookRepository;
    private final BookRepository bookRepository;
    private final BestbookRepository bestbookRepository;
    private final EntityManager em;

    //inventorybook을 조회해서 베스트 책을 선별한다
    public Long createBestbookList(){
        return -1L;
    }

    public Page<Bestbook> getBestBook(LocalDate localDate, int groupBy, Pageable pageable){
        LocalDate from;
        if(groupBy==1) from = YearMonth.from(localDate).atDay(1);
        else from = localDate.with(DayOfWeek.MONDAY);
        Page<Bestbook> bestbooks = bestbookRepository.findByDateAndGroupBy(from, groupBy,pageable);
        return bestbooks;
    }
    public List<BookScoreDto> setBestbook(LocalDate localDate, int groupBy) {
        //groupBy : 1 => 월간 검색
        //groupBy : 2 => 주간 검색

        LocalDate from,to;
        if(groupBy ==1){
            YearMonth month = YearMonth.from(localDate);
            from = month.atDay(1);
            to = month.atEndOfMonth();
        }else {
            from = localDate.with(DayOfWeek.MONDAY);
            to = localDate.with(DayOfWeek.SUNDAY);
        }
        log.info("from : "+from);
        log.info("to : "+to);
        List<BookScoreDto> addScore = inventoryBookRepository.getBookAddScoreOn(from,to);
        List<BookScoreDto> readScore = inventoryBookRepository.getBookReadScoreOn(from,to);
        Map<Long, Integer> bookScores = new HashMap<>();

        int readWeight =2;
        for(BookScoreDto score : readScore){
            bookScores.put(score.getBookid(), readWeight * score.getScore());
        }
        for(BookScoreDto score : addScore){
            if(bookScores.get(score.getBookid())==null)
                bookScores.put(score.getBookid(), score.getScore());
            else{
                bookScores.replace(score.getBookid(),
                        bookScores.get(score.getBookid())+score.getScore());
            }
        }

        List<BookScoreDto> bestbookList = new ArrayList<>();
        for(Long key : bookScores.keySet()){
            bestbookList.add(new BookScoreDto(key, bookScores.get(key)));
        }
        bestbookList.sort(Comparator.reverseOrder());
        int rank =1;
        for(BookScoreDto score : bestbookList){
            score.setRank(rank);
            rank++;
            Book book = bookRepository.findById(score.getBookid()).get();
            Bestbook bestbook = new Bestbook(from,groupBy,score.getRank(),book,score.getScore());
            bestbookRepository.save(bestbook);
        }
        return bestbookList;
    }
}
