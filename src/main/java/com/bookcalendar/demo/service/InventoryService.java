package com.bookcalendar.demo.service;

import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.domain.Inventory;
import com.bookcalendar.demo.domain.InventoryBook;
import com.bookcalendar.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryV1Repository inventoryV1Repository;
    private final InventoryBookV1Repository inventoryBookV1Repository;
    private final BookV1Repository bookV1Repository;
    private final InventoryRepository inventoryRepository;
    private final BookRepository bookRepository;
    private final InventoryBookRepository inventoryBookRepository;

    //특정 유저의 책 목록 조회
    public List<InventoryBook> findInventoryBookList(Long memberId){
        Inventory inventory= inventoryRepository.findByMemberId(memberId);
        List<InventoryBook> inventoryBookList = inventoryBookV1Repository.findByInventoryId(inventory.getId());
        return inventoryBookList;
    }

    @Transactional//책 담기
    public Long addBook(Long memberId, Long bookId){
        Inventory inventory= inventoryRepository.findByMemberId(memberId);
        Book book = bookRepository.getOne(bookId);
        InventoryBook inventoryBook = InventoryBook.createInventoryBook(book);
        inventory.addBook(inventoryBook);
        InventoryBook savedBook = inventoryBookRepository.save(inventoryBook);
        return  savedBook.getId();
    }

    //책 빼기
    public Long removeBook(Long memberId, Long inventoryBookId){
        Inventory inventory = inventoryV1Repository.findByMemberId(memberId);
        InventoryBook inventoryBook = inventoryBookV1Repository.findOne(inventoryBookId);
        inventoryBookV1Repository.removeOne(inventoryBook);
        return  inventory.getId();
    }


}
