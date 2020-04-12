package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Bestbook;
import com.bookcalendar.demo.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>, BookRepositoryCustom {
    @Query("select b from Book b " +
            " where b.title like %?1% " +
            " or b.author like %?1% " +
            " or b.isbn like %?1% ")
    Page<Book> getSearchList(String search, Pageable pageable);

    //select RANK() OVER (ORDER BY count(*)), Book.Book_id, count(*) as score , Inventory_book.add_date
    // from Book join Inventory_book  on (Book.book_id=Inventory_book.book_id)
    // where Inventory_book.add_date ='2020-04-12' Group by book.Book_id;
    //@Query("select new com.bookcalendar.demo.domain.Bestbook(rank() over(order by count(*)) )" +
    //        " from Book b join InventoryBook ib on (b.id = ib.book.id)" +
    //        " where ib.addDate = ?1 GROUP BY b.id")
    //List<Bestbook> getBestbookList(LocalDate date);

}
