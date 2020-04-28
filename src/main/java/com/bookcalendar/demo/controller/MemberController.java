package com.bookcalendar.demo.controller;

import com.bookcalendar.demo.domain.Inventory;
import com.bookcalendar.demo.domain.InventoryBook;
import com.bookcalendar.demo.domain.Member;
import com.bookcalendar.demo.dto.InventoryBookDto;
import com.bookcalendar.demo.repository.InventoryBookRepository;
import com.bookcalendar.demo.repository.InventoryRepository;
import com.bookcalendar.demo.service.InventoryService;
import com.bookcalendar.demo.service.MemberService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final InventoryService inventoryService;
    private final InventoryRepository inventoryRepository;
    private final InventoryBookRepository inventoryBookRepository;

    @GetMapping("/members/join")
    public String joinForm(Model model){
        model.addAttribute("member",new MemberForm());
        return "members/joinForm";
    }

    @PostMapping("/members/join")
    public String join(Model model,MemberForm memberForm){
        Member member = Member.createMember(
                                memberForm.getUserid()
                                ,memberForm.getPassword()
                                ,memberForm.getUsername()
                                ,memberForm.getNickname());
        try {
            memberService.join(member);
        }catch (IllegalStateException e){
/** 이상한 처리임 ㅎㅎ **/
            memberForm.setUserid("");
            model.addAttribute("member",memberForm);
            return "members/joinForm";
        }
        return "redirect:/";
    }

    @GetMapping("/members/login")
    public String loginForm(Model model, HttpServletRequest request){
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("prevPage",referrer);
        model.addAttribute("member",new MemberForm());
        return "members/loginForm";
    }

    @PostMapping("/members/login")
    public String login(MemberForm memberForm, HttpServletRequest request){
        HttpSession session = request.getSession();

        Long memberId = memberService.validateLoginInfo(memberForm.getUserid(), memberForm.getPassword());
        Member member = memberService.findMember(memberId);
        session.setAttribute("member",member);
        String referrer =(String) session.getAttribute("prevPage");
        session.removeAttribute("prevPage");
        //다른 사이트에서 url로만 접근한 경우 처리해야함.
        return "redirect:"+referrer;
    }

    @GetMapping("/members/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("member");
        //session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/members/{inventoryId}/add/book/{bookId}")
    @ResponseBody
    public ResponseEntity<Object> addBookToInventory(@PathVariable Long inventoryId, @PathVariable Long bookId, HttpSession session, Model model){

       try{
           Long savedInventoryBookId = inventoryService.addBook(inventoryId, bookId);
           return new ResponseEntity<>(savedInventoryBookId.toString(),HttpStatus.OK);
       }catch (IllegalStateException e){
           log.error("이미 등록된 책입니다.");
           String message = "이미 등록된 책입니다";
           return new ResponseEntity<>(message,HttpStatus.CONFLICT);
       }
    }

    @GetMapping("/members/inventory/{inventoryId}")
    public String getInventoryBookList(@PathVariable Long inventoryId,
                                       @PageableDefault(page = 0,size = 6)Pageable pageable, //,sort = "addDate",direction = Sort.Direction.DESC
                                       Model model){
        //세션에 있는 member의 inventory는 프록시 객체임!!! id만 가지고 있음
        //Member member =(Member) session.getAttribute("member");
        //Inventory findInventory = member.getInventory();
        //log.info("=========="+findInventory.getId());
        //log.info("=========="+findInventory.getInventoryBooks().size()); //초기화 안되이있어서 오류터짐

        Inventory inventory = inventoryRepository.findById(inventoryId).get();
        Page<InventoryBookDto> inventoryBookList = inventoryBookRepository.getDtosByInventoryId(inventoryId,pageable);
        model.addAttribute("inventory",inventory);
        model.addAttribute("bookList",inventoryBookList);
        return "inventories/inventory";
    }



}
