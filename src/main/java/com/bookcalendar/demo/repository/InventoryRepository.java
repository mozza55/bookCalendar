package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Book;
import com.bookcalendar.demo.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    public Inventory findByMemberId(Long id);

}
