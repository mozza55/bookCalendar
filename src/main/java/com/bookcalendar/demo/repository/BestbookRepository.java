package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Bestbook;
import com.bookcalendar.demo.domain.InventoryBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BestbookRepository extends JpaRepository<Bestbook,Long> {

    @EntityGraph(attributePaths ="book")
    //@Query("select bb from Bestbook bb where ib.inventory.id = ?1")
    Page<Bestbook> findByDateAndGroupBy(LocalDate date, int groupBy, Pageable pageable);



}
