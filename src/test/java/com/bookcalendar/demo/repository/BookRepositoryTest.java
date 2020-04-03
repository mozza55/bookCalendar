package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.domain.QBook;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class BookRepositoryTest {

    @Autowired BookRepository bookRepository;

    @Test
    public void testQuerydsl() throws Exception{
        log.info("proxy : "+bookRepository.getClass());
        //given
        Book book = Book.createBook("제목입니다","작가","123",200);
        bookRepository.save(book);
        //when
        Predicate predicate =  QBook.book.title.containsIgnoreCase("입")
                .and(QBook.book.author.startsWithIgnoreCase("작"));
        Optional<Book> findBook = bookRepository.findOne(predicate);
        //then
        assertEquals(findBook.orElseThrow(()->new EntityNotFoundException()).getId(),book.getId());
    }
}