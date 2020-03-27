package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class InventoryRepository {

    private final EntityManager em;

    public void save(Inventory inventory){
        em.persist(inventory);
    }

    public Inventory findOne(Long id){
        return em.find(Inventory.class, id);
    }

    //예외처리해줘야함 0개나 2개 이상일 때
    public Inventory findByMemberId(Long memberId){
        //select i from Inventory i join i.Member m
        return em.createQuery("select i from Inventory i join Member m where m.id = :memberId",Inventory.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }
}
