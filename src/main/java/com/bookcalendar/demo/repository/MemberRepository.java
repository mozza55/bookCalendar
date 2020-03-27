package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor //생성자 인젝션
public class MemberRepository {

    private final EntityManager em; //실행 중간에 변경 될 필요가 없기 때문에 final로
    /*
    @PersistenceContext  스프링 데이터 jpa를 사용하면  @Autowired로 대체가능
    생성자가 하나일 때는 @Autowired 생략 가능
    @RequiredArgsConstructor 는 파이널 필드를 가지는 생성자 생성
    => @RequiredArgsConstructor 한 줄로 생성자 인젝션

    @PersistenceContext
    public MemberRepository(EntityManager em) {
        this.em = em;
    }
     */

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }
    public Member findOne(Long id){
        return em.find(Member.class,id);
    }

    public List<Member> findAll(){
        return em.createQuery("select u from Member u", Member.class)
                .getResultList();
    }

    public Member findByUserId(String userid) throws NoResultException, NonUniqueResultException {
        return em.createQuery("select u from Member u where u.userid =:userid", Member.class)
                .setParameter("userid",userid)
                .getSingleResult();
    }



}
