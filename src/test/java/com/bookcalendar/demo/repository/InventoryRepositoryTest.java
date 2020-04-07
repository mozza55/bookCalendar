package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class InventoryRepositoryTest {

    @Autowired InventoryRepository inventoryRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired InventoryBookRepository inventoryBookRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired EntityManager em;
    @Before
    public void beforeTest(){
        Member member = new Member();
        member.setUserid("abc");
        member.setPassword("123");
        member.setNickname("망고");
        Inventory inventory = new Inventory();
        Calendar calendar = new Calendar();
        member.setCalendar(calendar);
        member.setInventory(inventory);
        Long memberId = memberRepository.save(member);


    }

    @Test
    public void testFindByIdWithInventory() throws Exception{
        //given

        Book book = Book.createBook("제목","작가","출판사","123",200);
        bookRepository.save(book);
        InventoryBook inventoryBook = InventoryBook.createInventoryBook(book);
        Long memberId = memberRepository.findByUserId("abc").getId();
        Inventory inventory = inventoryRepository.findByMemberId(memberId);
        inventory.addBook(inventoryBook);
        inventoryBookRepository.save(inventoryBook);
        em.flush();
        em.clear();
        //when
        log.info("===========쿼리 몇개 날라가는지 확인");
        Inventory findInventory = inventoryRepository.findByIdWithInventoryBook(inventory.getId());
        //then
        Assertions.assertEquals(findInventory.getInventoryBooks().get(0).getId(),inventoryBook.getId());
    }
    @Test
    public void TestFindByMemberId() throws Exception{
        //given
        Long memberId = memberRepository.findByUserId("abc").getId();

        //when
        Inventory findInventory = inventoryRepository.findByMemberId(memberId);
        //then

        Assertions.assertEquals(findInventory.getMember().getId(),memberId);
    }
}