package com.bookcalendar.demo.service;

import com.bookcalendar.demo.domain.Member;
import com.bookcalendar.demo.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // 스프링을 인티그레이션 해서 테스트
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setUserid("mozza5");
        member.setNickname("모짜");
        member.setUsername("잠모짜");

        //when
        Long savedId = memberService.join(member);
        Member findMember = memberRepository.findOne(savedId);
        //then
        em.flush();
        assertEquals(member,findMember);
        assertEquals(member.getCalendar().getId(),findMember.getCalendar().getId());
        assertEquals(member.getInventory().getId(),findMember.getInventory().getId());
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_에외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setUserid("abc");

        Member member2 = new Member();
        member2.setUserid("abc");

        //when
        memberService.join(member1);
        /*
        try {
            memberService.join(member2); //예외가 발생해야 한다!
        }catch (IllegalStateException e){
            return;
        }*/
        memberService.join(member2);
        //then

        fail("예외가 발생 해야 한다.");
    }
}