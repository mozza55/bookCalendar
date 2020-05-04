package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.InventoryBook;
import com.bookcalendar.demo.dto.InventoryBookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InventoryBookRepository  extends JpaRepository<InventoryBook,Long>, InventoryBookRepositoryCustom {

    List<InventoryBook> findByInventoryIdAndBookId(Long inventoryId, Long BookId);

    @EntityGraph(attributePaths ="book")
    @Query("select ib from InventoryBook ib where ib.inventory.id = ?1")
    Page<InventoryBook> findByInventoryIdWithBook(Long inventoryId, Pageable pageable);

    @Query("select ib from InventoryBook ib where ib.inventory.id =?1 and ib.book.id = ?2")
    InventoryBook findFirstByInventoryIdAndBookId(Long inventoryId, Long bookId);

    /* 요소에 대해 각각 호출..
    @Query("select new com.bookcalendar.demo.dto.InventoryBookWithBookDto(ib, ib.book) " +
            "from InventoryBook ib where ib.inventory.id =?1")
    Page<InventoryBookWithBookDto> findDtosByInventoryId(Long inventoryId, Pageable pageable);

     */
    @EntityGraph(attributePaths ="book")
    @Query("select ib from InventoryBook ib where ib.inventory.id = ?1")
    List<InventoryBook> findByInventoryIdWithBook(Long inventoryId);


    @Modifying //update, delete query 시 사용하는 어노테이션 return; number of rows
    @Transactional
    @Query("update InventoryBook ib set ib.currentPage =?2 where ib.id=?1")
    int updateCurrentPage(Long inventoryBookId, int page);

    @Modifying //update, delete query 시 사용하는 어노테이션 return; number of rows
    @Transactional
    @Query("update InventoryBook ib set ib.rating =?2 where ib.id=?1")
    int updateRating(Long inventoryBookId, int rating);


    @Query("select new com.bookcalendar.demo.dto.InventoryBookDto(ib.id,ib.book.id,ib.status,ib.currentPage,ib.page,ib.addDate,ib.readDate,ib.rating,ib.comment)" +
            "from InventoryBook ib where ib.id =?1")
    InventoryBookDto findDtoById(Long id);

}
