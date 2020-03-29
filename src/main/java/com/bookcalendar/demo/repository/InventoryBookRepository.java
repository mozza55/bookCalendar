package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.InventoryBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryBookRepository  extends JpaRepository<InventoryBook,Long> {
}
