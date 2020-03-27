package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.InventoryBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InventoryBookRepository {
    private final EntityManager em;

    //특정 인벤토리에 있는 책 목록 조회
    public List<InventoryBook> findByInventoryId(Long inventoryId){
        return em.createQuery("select b from InventoryBook b join Inventory i where i.id = :inventoryId ", InventoryBook.class)
                .setParameter("inventoryId", inventoryId)
                .getResultList();
    }

    public InventoryBook findOne(Long inventoryBookId){
        return em.find(InventoryBook.class,inventoryBookId);
    }

    public void removeOne(InventoryBook inventoryBook){
        em.remove(inventoryBook);
    }
}
