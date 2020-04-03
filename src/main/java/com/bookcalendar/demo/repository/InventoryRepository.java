package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    Inventory findByMemberId(Long id);

    @Query("select distinct i from Inventory i join fetch i.inventoryBooks")
    Inventory findByMemberIdWithInventoryBook(Long id);

    @Query("select distinct i from Inventory i join fetch i.inventoryBooks where i.id=?1")
    Inventory findByIdWithInventoryBook(Long inventoryId);

    @Override
    Optional<Inventory> findById(Long aLong);
}
