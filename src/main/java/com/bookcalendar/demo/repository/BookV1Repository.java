package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookV1Repository {
    private final EntityManager em;

    public void save(Book book){
        if(book.getId() == null){
            em.persist(book);
        }else{
            em.merge(book);
        }
    }

    public Book findOne(Long id){
        return em.find(Book.class,id);
    }

    public List<Book> findAll(){
        return em.createQuery("select b from Book b",Book.class)
                .getResultList();
    }

    public List<Book> findAll(int startPosition, int maxResult){
        return em.createQuery("select b from Book b",Book.class)
                .setFirstResult(startPosition)
                .setMaxResults(maxResult)
                .getResultList();
    }


    public Long getSize() {
        return em.createQuery("select count(b) from Book b",Long.class)
                .getSingleResult();
    }
}
