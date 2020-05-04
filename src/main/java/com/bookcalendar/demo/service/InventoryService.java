package com.bookcalendar.demo.service;

import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.domain.Inventory;
import com.bookcalendar.demo.domain.InventoryBook;
import com.bookcalendar.demo.domain.ReadStatus;
import com.bookcalendar.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryV1Repository inventoryV1Repository;
    private final InventoryBookV1Repository inventoryBookV1Repository;
    private final InventoryRepository inventoryRepository;
    private final BookRepository bookRepository;
    private final InventoryBookRepository inventoryBookRepository;

    //특정 유저의 책 목록 조회
    public List<InventoryBook> findInventoryBookList(Long inventoryId){
        Inventory inventory= inventoryRepository.findByMemberIdWithInventoryBook(inventoryId);
        List<InventoryBook> inventoryBookList = inventoryBookV1Repository.findByInventoryId(inventory.getId());
        return inventoryBookList;
    }
    public Inventory getInventoryWithInventoryBook(Long inventoryId){
        Inventory inventory = inventoryRepository.findByIdWithInventoryBook(inventoryId);
        return  inventory;
    }


    @Transactional//책 담기
    public Long addBook(Long inventoryId, Long bookId){
        //중복책 확인
        validateDuplicateBook(inventoryId,bookId);
        //책 추가
        Book book = bookRepository.getOne(bookId);
        Inventory inventory= inventoryRepository.getOne(inventoryId);
        InventoryBook inventoryBook = InventoryBook.createInventoryBook(book,inventory);
        InventoryBook savedBook = inventoryBookRepository.save(inventoryBook);
        return  savedBook.getId();
    }
    @Transactional//다 읽은 책 담기
    public Long addFinishedBook(Long inventoryId, Long bookId, int rating){
        log.info("rating gg : "+rating);
        //중복책 확인
        validateDuplicateBook(inventoryId,bookId);
        //책 추가
        Book book = bookRepository.getOne(bookId);
        Inventory inventory= inventoryRepository.getOne(inventoryId);
        InventoryBook inventoryBook = InventoryBook.createInventoryBook(book,inventory);

        //
        inventoryBook.finishInventoryBook();
        inventoryBook.setRating(rating);
        //
        InventoryBook savedBook = inventoryBookRepository.save(inventoryBook);

        return  savedBook.getId();
    }


    public void validateDuplicateBook(Long inventoryId,Long bookId){
        if(inventoryBookRepository.findByInventoryIdAndBookId(inventoryId,bookId).size() !=0){
            throw new IllegalStateException("이미 등록된 책입니다");
        }
    }

    @Transactional
    public void updateRating(Long inventorybookId, Integer rating) {
        InventoryBook inventoryBook = inventoryBookRepository.getOne(inventorybookId);
        inventoryBook.finishInventoryBook();
        inventoryBook.setRating(rating);
    }

    @Transactional
    public void deleteInventoryBook(Long inventoryBookId) {
        InventoryBook inventoryBook = inventoryBookRepository.findById(inventoryBookId).get();
        inventoryBook.removeInventoryBook();
        inventoryBookRepository.delete(inventoryBook);
    }
}
