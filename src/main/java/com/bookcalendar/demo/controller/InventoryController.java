package com.bookcalendar.demo.controller;

import com.bookcalendar.demo.domain.Inventory;
import com.bookcalendar.demo.domain.InventoryBook;
import com.bookcalendar.demo.domain.ReadStatus;
import com.bookcalendar.demo.dto.InventoryBookDto;
import com.bookcalendar.demo.dto.InventoryBookWithBookDto;
import com.bookcalendar.demo.repository.InventoryBookRepository;
import com.bookcalendar.demo.repository.InventoryRepository;
import com.bookcalendar.demo.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    public final InventoryService inventoryService;
    public final InventoryRepository inventoryRepository;
    public final InventoryBookRepository inventoryBookRepository;


    @GetMapping("/members/inventory/{inventoryId}")
    public String getInventoryBookList(@PathVariable Long inventoryId,
                                       @PageableDefault(page = 0,size = 6) Pageable pageable, //,sort = "addDate",direction = Sort.Direction.DESC
                                       Model model){
        //세션에 있는 member의 inventory는 프록시 객체임!!! id만 가지고 있음
        //Member member =(Member) session.getAttribute("member");
        //Inventory findInventory = member.getInventory();
        //log.info("=========="+findInventory.getId());
        //log.info("=========="+findInventory.getInventoryBooks().size()); //초기화 안되이있어서 오류터짐

        Inventory inventory = inventoryRepository.findById(inventoryId).get();
        Page<InventoryBookWithBookDto> inventoryBookList = inventoryBookRepository.getDtosByInventoryId(inventoryId,pageable);
        model.addAttribute("inventory",inventory);
        model.addAttribute("bookList",inventoryBookList);
        return "inventories/inventory";
    }


    @GetMapping("/inventorybooks")
    @ResponseBody
    public ResponseEntity<Object> getInventorybook(@RequestParam Long inventoryId,
                                   @RequestParam Long bookId){
        InventoryBook findBook = inventoryBookRepository.findFirstByInventoryIdAndBookId(inventoryId, bookId);
        if(findBook != null){
            InventoryBookDto inventoryBook = inventoryBookRepository.findDtoById(findBook.getId());
            return new ResponseEntity<>(inventoryBook, HttpStatus.OK);
        }
        else{
            String message ="inventory에 포함하지 않은 도서";
            return new ResponseEntity<>(message,HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/inventorybooks")
    @ResponseBody
    public ResponseEntity<Object> addBookToInventory(@RequestParam Long inventoryId,
                                                     @RequestParam Long bookId,
                                                     @RequestParam(required = false) Integer rating){
        try{
            Long savedInventoryBookId;
            if(rating ==null){
                log.info("rating==null");
                savedInventoryBookId = inventoryService.addBook(inventoryId, bookId);
            }else{
                log.info("rating != null ; rating =="+rating);
                savedInventoryBookId= inventoryService.addFinishedBook(inventoryId,bookId,rating);
            }
            InventoryBookDto inventoryBook = inventoryBookRepository.findDtoById(savedInventoryBookId);
            return new ResponseEntity<>(inventoryBook, HttpStatus.OK);
        }catch (IllegalStateException e){
            log.error("이미 등록된 책입니다.");
            String message = "이미 등록된 책입니다";
            return new ResponseEntity<>(message,HttpStatus.CONFLICT);
        }
    }
    @PutMapping("/inventorybooks")
    @ResponseBody
    public ResponseEntity<Object> updateRatingOfInventoryBook(@RequestBody Long inventoryBookId,
                                                              @RequestBody Integer rating,
                                                              @RequestBody(required = false) ReadStatus status){
        if(status!=null) log.info("status: "+status);
        inventoryService.updateRating(inventoryBookId, rating);
        InventoryBookDto inventoryBookDto = inventoryBookRepository.findDtoById(inventoryBookId);
        return new ResponseEntity<>(inventoryBookDto,HttpStatus.OK);
    }

}
