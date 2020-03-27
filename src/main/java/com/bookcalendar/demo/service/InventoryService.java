package com.bookcalendar.demo.service;

import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.domain.Inventory;
import com.bookcalendar.demo.domain.InventoryBook;
import com.bookcalendar.demo.repository.BookV1Repository;
import com.bookcalendar.demo.repository.InventoryBookRepository;
import com.bookcalendar.demo.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryBookRepository inventoryBookRepository;
    private final BookV1Repository bookV1Repository;

    //특정 유저의 책 목록 조회
    public List<InventoryBook> findInventoryBooks(Long memberId){
        Inventory inventory= inventoryRepository.findByMemberId(memberId);
        List<InventoryBook> inventoryBookList = inventoryBookRepository.findByInventoryId(inventory.getId());
        return inventoryBookList;
    }

    //책 담기
    public Long addBook(Long memberId, Long bookId){
        Inventory inventory= inventoryRepository.findByMemberId(memberId);
        Book book = bookV1Repository.findOne(bookId);
        InventoryBook inventoryBook = InventoryBook.createInventoryBook(book);
        inventory.addBook(inventoryBook);
        return  inventoryBook.getId();
    }

    //책 빼기
    public Long removeBook(Long memberId, Long inventoryBookId){
        Inventory inventory = inventoryRepository.findByMemberId(memberId);
        InventoryBook inventoryBook = inventoryBookRepository.findOne(inventoryBookId);
        inventoryBookRepository.removeOne(inventoryBook);
        return  inventory.getId();
    }


}
