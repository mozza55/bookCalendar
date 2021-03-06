package com.bookcalendar.demo.controller;

import com.bookcalendar.demo.domain.Bestbook;
import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.dto.EventDto;
import com.bookcalendar.demo.repository.BestbookRepository;
import com.bookcalendar.demo.repository.BookRepository;
import com.bookcalendar.demo.repository.BookScoreDto;
import com.bookcalendar.demo.repository.BookSearch;
import com.bookcalendar.demo.service.BestbookService;
import com.bookcalendar.demo.service.BookService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;
    private final BestbookService bestbookService;
    private final BestbookRepository bestbookRepository;

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

    @GetMapping("/v2/books")
    public String getBookListV1(@RequestParam(value = "page", defaultValue = "1")int page, @RequestParam(value = "sort", defaultValue = "readCount")String sort, Model model){
        Page<Book> bookList = bookService.getBookList(page,3, sort);
        model.addAttribute("bookList",bookList);
        String orderBy = sort;
        model.addAttribute("orderBy",orderBy);
        return "books/bookIndex";
    }

    @GetMapping("/books/detail/{id}")
    public String bookDetail(@PathVariable Long id, Model model){
        Book book = bookRepository.getOne(id);
        model.addAttribute("book",book);
        model.addAttribute("bookSearch",new BookSearch());

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
        return "books/bookIndex";
    }
    @PostMapping("/books/search1")
    public String searchBook1(@PageableDefault(page=0, size =1, sort = "readCount",direction = Sort.Direction.DESC) Pageable pageable,
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

        return "books/searchList";
    }

    @GetMapping("/books/search")
    public String searchBook(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "readCount") String sortBy,
                                 BookSearch bookSearch, Model model){
        int size =3; //pagesize
        log.info("검색어, 검색 조건 : "+bookSearch.searchWord+", "+bookSearch.searchField);
        log.info("page, sortBy :"+page+", "+sortBy);
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,sortBy);
        model.addAttribute("sortBy",sortBy);

        Page<Book> searchList = bookRepository.findAllByBookSearch(bookSearch, pageable);
        model.addAttribute("bookList",searchList);
        model.addAttribute("bookSearch",bookSearch);


        return "books/searchList";
    }

    @GetMapping("/books/bestseller")
    public String searchBestBook(
            @RequestParam(defaultValue = "1") int groupBy,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer week,
            @RequestParam(defaultValue = "0")int page,
                                 Model model){
        //groupBy == 1 월간 검색
        //groupBy == 2 주간 검색
        int size =3; //pagesize
        LocalDate date =LocalDate.now();
        SearchDate searchDate;
        if(month !=null) date = LocalDate.of(year,month,1);
        if(week!=null){
            switch (week) {
                case 1: date =date.withDayOfMonth(1); break;
                case 2: date =date.withDayOfMonth(8); break;
                case 3: date =date.withDayOfMonth(15); break;
                case 4: date =date.withDayOfMonth(22); break;
                case 5: date =YearMonth.from(date).atEndOfMonth(); break;
                default: break;
            }
            searchDate = new SearchDate(date.getYear(),date.getMonthValue(),week);
        }else searchDate = new SearchDate(date.getYear(),date.getMonthValue(),(date.getDayOfMonth()-1)/7+1);
        log.info("date : "+date);
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"rank");
        Page<Bestbook> searchList = bestbookService.getBestBook(date,groupBy,pageable);
        model.addAttribute("bookList",searchList);
        model.addAttribute("bookSearch",new BookSearch());
        model.addAttribute("groupBy",groupBy);
        model.addAttribute("searchDate",searchDate);
        return "books/bestseller";
    }

    @Data
    static class SearchDate{
        private int year;
        private int month;
        private int week;

        public SearchDate(int year, int month, int week) {
            this.year = year;
            this.month = month;
            this.week = week;
        }
    }
    @PostMapping("/books/test")
    @ResponseBody
    public String test(@RequestBody BookSearch bookSearch){
        log.info("name : "+bookSearch.searchWord);
        log.info("name : "+bookSearch.searchField);
        return bookSearch.toString();
    }

    @GetMapping("/books/best")
    @ResponseBody
    //bestbook list를 산출해줌
    public List<BookScoreDto> getBestbook(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate,
                                          @RequestParam(defaultValue = "1") int groupBy){
        return bestbookService.setBestbook(localDate,groupBy);
    }


    @PostMapping("/books/read")
    @ResponseBody
    public String readEvent(EventDto event){

        return null;
    }
}
