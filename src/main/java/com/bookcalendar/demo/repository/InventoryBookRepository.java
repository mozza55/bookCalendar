package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.InventoryBook;
import com.bookcalendar.demo.dto.InventoryBookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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


    /* 요소에 대해 각각 호출..
    @Query("select new com.bookcalendar.demo.dto.InventoryBookDto(ib, ib.book) " +
            "from InventoryBook ib where ib.inventory.id =?1")
    Page<InventoryBookDto> findDtosByInventoryId(Long inventoryId, Pageable pageable);

     */
    @EntityGraph(attributePaths ="book")
    @Query("select ib from InventoryBook ib where ib.inventory.id = ?1")
    List<InventoryBook> findByInventoryIdWithBook(Long inventoryId);


    @Modifying //update, delete query 시 사용하는 어노테이션 return; number of rows
    @Query("update InventoryBook ib set ib.currentPage =?2 where ib.id=?1")
    int updateCurrentPage(Long inventoryBookId, int page);


}
