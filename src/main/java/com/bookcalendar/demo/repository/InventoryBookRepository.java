package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.InventoryBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryBookRepository  extends JpaRepository<InventoryBook,Long> {

    List<InventoryBook> findByInventoryIdAndBookId(Long inventoryId, Long BookId);

    @Query("select ib from InventoryBook ib join fetch ib.book b where ib.inventory.id = ?1")
    List<InventoryBook> findByInventoryIdWithBook(Long inventoryId);
}
