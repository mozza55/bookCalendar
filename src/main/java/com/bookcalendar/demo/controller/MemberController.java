package com.bookcalendar.demo.controller;

import com.bookcalendar.demo.domain.Member;
import com.bookcalendar.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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
    public String loginForm(Model model){
        model.addAttribute("member",new MemberForm());
        return "members/loginForm";
    }

    @PostMapping("/members/login")
    public String login(MemberForm memberForm, HttpServletRequest request){
        HttpSession session = request.getSession();

        Long memberId = memberService.validateLoginInfo(memberForm.getUserid(), memberForm.getPassword());
        Member member = memberService.findMember(memberId);
        session.setAttribute("member",member);
        return "redirect:/";
    }

    @GetMapping("/members/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("member");
        //session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/members/{memberId}/add/book/{bookId}")
    public String addBookToInventory(@PathVariable Long memberId, @PathVariable Long bookId, Model model){
        System.out.println("========memberId"+memberId);
        return "redirect:/";
    }
}
