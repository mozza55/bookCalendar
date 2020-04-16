package com.bookcalendar.demo.service;

import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.domain.Inventory;
import com.bookcalendar.demo.domain.InventoryBook;
import com.bookcalendar.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        book.addAddCount();
        InventoryBook inventoryBook = InventoryBook.createInventoryBook(book);
        Inventory inventory= inventoryRepository.getOne(inventoryId);
        inventory.addBook(inventoryBook);
        InventoryBook savedBook = inventoryBookRepository.save(inventoryBook);
        return  savedBook.getId();
    }

    public void validateDuplicateBook(Long inventoryId,Long bookId){
        if(inventoryBookRepository.findByInventoryIdAndBookId(inventoryId,bookId).size() !=0){
            throw new IllegalStateException("이미 등록된 책입니다");
        }
    }
    //책 빼기
    public Long removeBook(Long memberId, Long inventoryBookId){
        Inventory inventory = inventoryV1Repository.findByMemberId(memberId);
        InventoryBook inventoryBook = inventoryBookV1Repository.findOne(inventoryBookId);
        inventoryBookV1Repository.removeOne(inventoryBook);
        return  inventory.getId();
    }

}
