package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.domain.QBook;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;
    @Autowired EntityManager em;


    @Test
    public void testQuerydsl() throws Exception{
        log.info("proxy : "+bookRepository.getClass());
        //given
        Book book1 = Book.createBook("제목입니다","작가","123",200);
        bookRepository.save(book1);
        Book book2 = Book.createBook("제목d","d","123",200);
        bookRepository.save(book2);
        //when
        Book findBook = bookRepository.findAllBySearch("목","d");
        //then
        log.info("Book1: "+book1.getId());
        log.info("Book2: "+book2.getId());
        log.info("findBook: "+findBook.getId());
        assertEquals(findBook.getId(),book2.getId());
    }

    @Test
    public void querydsl() throws Exception{
        Book book = Book.createBook("제목입니다","작가","123",200);
        bookRepository.save(book);
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QBook b = QBook.book;
        Book findBook = queryFactory
                .select(b)
                .from(b)
                .where(b.title.containsIgnoreCase("입")
                    .and(b.author.startsWithIgnoreCase("작")))
                .fetchOne();

        assertEquals(findBook.getTitle(),book.getTitle());
    }
}