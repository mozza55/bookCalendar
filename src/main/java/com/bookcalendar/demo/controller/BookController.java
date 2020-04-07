package com.bookcalendar.demo.controller;

import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.repository.BookRepository;
import com.bookcalendar.demo.repository.BookSearch;
import com.bookcalendar.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;

    @GetMapping("/books/create")
    public String createForm(Model model){
       // model.addAttribute(new Book());
        return "admin/bookForm";
    }

    @PostMapping("/books/create")
    public String createBook(@RequestParam(value = "title")String title,
                             @RequestParam(value = "author")String author,
                             @RequestParam(value = "publisher")String publisher,
                             @RequestParam(value = "isbn")String isbn,
                             @RequestParam(value = "page")int page ){
        Book book = Book.createBook(title,author,publisher,isbn,page);
        bookRepository.save(book);
        return "redirect:/admin";
    }

    //Pageable 사용 안하고 페이지네이션
    @GetMapping("/v1/books")
    public String getBookList2(@RequestParam(value = "page", required = false)Integer page, Model model){
        if(page ==null) page =1;
        int booksPerPage = 3;
        List<Book> bookList = bookService.getBookList(page,booksPerPage);
        Long totalBooks = bookService.getTotalBooks();
        Long totalPages =( (totalBooks%booksPerPage==0 )? totalBooks/booksPerPage: totalBooks/booksPerPage +1);

        model.addAttribute("books",bookList);
        model.addAttribute("page",page.intValue() );
        model.addAttribute("totalPages",totalPages);
        return "books/bookListV1";
    }

    @GetMapping("/v2/books")
    public String getBookListV1(@RequestParam(value = "page", defaultValue = "1")int page, @RequestParam(value = "sort", defaultValue = "readCount")String sort, Model model){
        Page<Book> bookList = bookService.getBookList(page,3, sort);
        model.addAttribute("bookList",bookList);
        String orderBy = sort;
        model.addAttribute("orderBy",orderBy);
        return "books/bookList";
    }

    @GetMapping("/books/detail/{id}")
    public String bookDetail(@PathVariable Long id, Model model){
        Book book = bookRepository.getOne(id);
        model.addAttribute("book",book);

        return "books/bookDetail";
    }
    @GetMapping("/books")
    public String getBookList(@PageableDefault(page = 1, size = 2, sort = "readCount",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Book> bookList = bookService.getBookList(pageable);
        model.addAttribute("bookList",bookList);
        String orderBy = pageable.getSort().toString().split(":")[0];
        model.addAttribute("orderBy",orderBy);
        model.addAttribute("bookSearch",new BookSearch());
        log.info("orderBy : "+orderBy);
        log.info("page : "+pageable.getPageNumber());
        return "books/bookList";
    }
    @PostMapping("/books/search")
    public String searchBook(@PageableDefault(page=0, size =1, sort = "readCount",direction = Sort.Direction.DESC) Pageable pageable,
                             BookSearch bookSearch, Model model){
        log.info("검색어 : "+bookSearch.searchWord);
        log.info("검색조건 : "+bookSearch.searchField);
        log.info(pageable.getSort().toString());
        String orderBy = pageable.getSort().toString().split(": ")[0]+","+pageable.getSort().toString().split(": ")[1];
        model.addAttribute("orderBy",orderBy);
        log.info("orderBy : "+orderBy);

        Page<Book> searchList = bookRepository.findAllByBookSearch(bookSearch, pageable);
        model.addAttribute("bookList",searchList);
        model.addAttribute("bookSearch",bookSearch);

        return "books/bookList";
    }

    @PostMapping("/books/test")
    @ResponseBody
    public String test(@RequestBody BookSearch bookSearch){
        log.info("name : "+bookSearch.searchWord);
        log.info("name : "+bookSearch.searchField);
        return bookSearch.toString();
    }
}
