package com.bookcalendar.demo.controller;

import com.bookcalendar.demo.domain.Member;
import com.bookcalendar.demo.service.InventoryService;
import com.bookcalendar.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final InventoryService inventoryService;

    @GetMapping("/members/join")
    public String joinForm(Model model){
        model.addAttribute("member",new MemberForm());
        return "members/joinForm";
    }

    @PostMapping("/members/join")
    public String join(Model model,MemberForm memberForm){


        Member member = new Member();
        member.setUserid(memberForm.getUserid());
        member.setNickname(memberForm.getNickname());
        member.setPassword(memberForm.getPassword());
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

    @GetMapping("/members/{memberId}/add/book/{bookId}")
    @ResponseBody
    public String addBookToInventory(@PathVariable Long memberId, @PathVariable Long bookId, HttpSession session, Model model){
        Long savedInventoryBookId = inventoryService.addBook(memberId, bookId);
        return savedInventoryBookId.toString();
    }

}
