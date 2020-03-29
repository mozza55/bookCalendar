package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Calendar;
import com.bookcalendar.demo.domain.Inventory;
import com.bookcalendar.demo.domain.Member;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class InventoryRepositoryTest {

    @Autowired InventoryRepository inventoryRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    public void TestFindByMemberId() throws Exception{
        //given
        Member member = new Member();
        member.setUserid("abc");
        member.setPassword("123");
        member.setNickname("망고");
        Inventory inventory = new Inventory();
        Calendar calendar = new Calendar();
        member.setCalendar(calendar);
        member.setInventory(inventory);
        Long memberId = memberRepository.save(member);

        //when
        Inventory findInventory = inventoryRepository.findByMemberId(memberId);
        //then

        Assertions.assertEquals(findInventory.getMember().getId(),memberId);
    }
}