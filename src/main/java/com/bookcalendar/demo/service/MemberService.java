package com.bookcalendar.demo.service;

import com.bookcalendar.demo.domain.Calendar;
import com.bookcalendar.demo.domain.Inventory;
import com.bookcalendar.demo.domain.Member;
import com.bookcalendar.demo.exception.WrongPasswordException;
import com.bookcalendar.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

@Service
@Transactional
// @AllArgsConstructor // lombok 모든 필드로 생성자 만들어줌
@RequiredArgsConstructor //final로 지정한 필드 생성자 생성
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        Inventory inventory = new Inventory();
        Calendar calendar = new Calendar();
        member.setCalendar(calendar);
        member.setInventory(inventory);
        return memberRepository.save(member);
    }

    //중복 id 회원 검증
    private void validateDuplicateMember(Member member) {
        try{
            memberRepository.findByUserId(member.getUserid());
        }catch(NoResultException e){
            return;
        }
        throw new IllegalStateException("이미 존재하는 ID 입니다!");
    }

    //회원 전체 조회 ; 이런건 리포지토리를 바로 호출해서 사용해도 무방
    @Transactional(readOnly = true)
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원 조회
    @Transactional(readOnly = true)
    public Member findMember(Long memberId){
        return memberRepository.findOne(memberId);
    }


    public Long validateLoginInfo(String userId, String password){
        /**
         *
         잘못된 아이디 검증 필요
         */

        Member findMember = memberRepository.findByUserId(userId);

        if(!findMember.getPassword().equals(password)){
            throw new WrongPasswordException("잘못된 비밀번호 입니다.");
        }
        return findMember.getId();
    }

}
