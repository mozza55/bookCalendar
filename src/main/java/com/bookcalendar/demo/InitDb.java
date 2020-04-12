package com.bookcalendar.demo;


import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.domain.Member;
import com.bookcalendar.demo.repository.BookRepository;
import com.bookcalendar.demo.repository.InventoryRepository;
import com.bookcalendar.demo.repository.MemberRepository;
import com.bookcalendar.demo.service.InventoryService;
import com.bookcalendar.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;


@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;
    @PostConstruct //의존성 주입이 이루어진 후 초기화를 수행하는 메서드이다
    public void init(){
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        private final MemberService memberService;
        private final InventoryService inventoryService;
        private final BookRepository bookRepository;
        public void dbInit1(){
            Member member1 = Member.createMember("aaa", "1234", "김망고", "망고");
            memberService.join(member1);
            Member member2 = Member.createMember("bbb", "1234", "구아바", "아바");
            memberService.join(member2);

            Book book1 = Book.createBook("아몬드","손원평","창비","9788936434267",264);
            bookRepository.save(book1);

            Book book2 = Book.createBook("피프티 피플","정세랑","창비","9788936434243",396);
            bookRepository.save(book2);

            Book book3 = Book.createBook("보건교사 안은영","정세랑","민음사","9788937473098",280);
            bookRepository.save(book3);

            Book book4 = Book.createBook("안녕 주정뱅이","권여선","창비","9788936437381",276);
            bookRepository.save(book4);

            Book book5 = Book.createBook("진이, 지니","정유정","은행나무","9791189982140",388);
            bookRepository.save(book5);

            inventoryService.addBook(member1.getInventory().getId(), book3.getId());
            inventoryService.addBook(member1.getInventory().getId(), book2.getId());
            inventoryService.addBook(member2.getInventory().getId(), book3.getId());
        }

    }
}

