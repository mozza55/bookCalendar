package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void userTest() throws Exception{
        //given
        Member member = new Member();
        member.setUsername("kim");
        member.setNickname("고구마");
        Long savedId = memberRepository.save(member);
        //when
        Member findMember = memberRepository.findOne(savedId);
        //then
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    }

}