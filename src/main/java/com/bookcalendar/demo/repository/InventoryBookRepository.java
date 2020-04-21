package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.InventoryBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryBookRepository  extends JpaRepository<InventoryBook,Long>, InventoryBookRepositoryCustom {

    List<InventoryBook> findByInventoryIdAndBookId(Long inventoryId, Long BookId);

    @EntityGraph(attributePaths ="book")
    @Query("select ib from InventoryBook ib where ib.inventory.id = ?1")
    Page<InventoryBook> findByInventoryIdWithBook(Long inventoryId, Pageable pageable);

    @EntityGraph(attributePaths ="book")
    @Query("select ib from InventoryBook ib where ib.inventory.id = ?1")
    List<InventoryBook> findByInventoryIdWithBook(Long inventoryId);


}
