package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.domain.InventoryBook;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class InventoryBookRepositoryTest {

    @Autowired InventoryBookRepository inventoryBookRepository;
    @Autowired InventoryRepository inventoryRepository;
    @Autowired
    BookRepository bookRepository;
    //private Logger logger = LoggerFactory.getLogger(SpringRunner.class);

    @Test
    public void testSave() throws Exception{
        //given
        Book book = Book.createBook("제목","작가","1234",200);
        bookRepository.save(book);
        InventoryBook inventoryBook = InventoryBook.createInventoryBook(book);
        //when
        InventoryBook savedBook = inventoryBookRepository.save(inventoryBook);
        //then
        assertEquals(savedBook.getId(), inventoryBook.getId());
        assertEquals(savedBook.getId(), inventoryBook.getId());
        log.info("값 비교 "+savedBook.getId()+"vs"+inventoryBook.getId());
    }

}