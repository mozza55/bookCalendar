package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.domain.Member;
import com.bookcalendar.demo.domain.QBook;
import com.bookcalendar.demo.service.InventoryService;
import com.bookcalendar.demo.service.MemberService;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class BookRepositoryTest {

    @Autowired BookRepository bookRepository;
    @Autowired MemberService memberService;
    @Autowired InventoryService inventoryService;
    @Autowired EntityManager em;


    @Test
    public void testQuerydsl() throws Exception{
        Member member1 = Member.createMember("aaa", "1234", "김망고", "망고");
        memberService.join(member1);
        Member member2 = Member.createMember("bbb", "1234", "구아바", "아바");
        memberService.join(member2);

        Book book1 = Book.createBook("아몬드","손원평","창비","9788936434267",264);
        bookRepository.save(book1);

        Book book2 = Book.createBook("피프티 피플","정세랑","창비","9788936434243",396);
        bookRepository.save(book2);

        Book book3 = Book.createBook("보건교사 안은영","정세랑","민음사","9788937473098",280);
        bookRepository.save(book3);

        Book book4 = Book.createBook("안녕 주정뱅이","권여선","창비","9788936437381",276);
        bookRepository.save(book4);

        Book book5 = Book.createBook("진이, 지니","정유정","은행나무","9791189982140",388);
        bookRepository.save(book5);

        inventoryService.addBook(member1.getInventory().getId(), book3.getId());
        inventoryService.addBook(member1.getInventory().getId(), book2.getId());
    }

    @Test
    public void querytest() throws Exception{

    }
}