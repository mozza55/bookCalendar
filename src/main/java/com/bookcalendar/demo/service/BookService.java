package com.bookcalendar.demo.service;

import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.repository.BookRepository;
import com.bookcalendar.demo.repository.BookV1Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.data.domain.Sort.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {
    private final BookV1Repository bookV1Repository;
    private final BookRepository bookRepository;

    public List<Book> getBookList(int page, int booksPerPage){
        //int booksPerPage = 1;
        //int page = (pageable.getPageNumber() ==0) ? 0 : (pageable.getPageNumber() -1);
        //List<String> list = List.of("list");
        //pageable = PageRequest.of(page,1, Sort.by(Sort.Direction.DESC,"book_id"));
        return  bookV1Repository.findAll((page-1)*booksPerPage, booksPerPage);
    }

    public Long getTotalBooks() {
        return bookV1Repository.getSize();
    }

    public Page<Book> getBookList(Pageable pageable, Direction direction, String orderBy){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, pageable.getPageSize(), by(direction,orderBy));


        return bookRepository.findAll(pageable);
    }

    public Page<Book> getBookList(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());

        return bookRepository.findAll(pageable);
    }

    public Page<Book> getBookList(int page, int size, String orderBy){
        page = (page==0) ? 0 : page - 1; // page는 index 처럼 0부터 시작
        Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.DESC,orderBy));

        return bookRepository.findAll(pageable);
    }

}
