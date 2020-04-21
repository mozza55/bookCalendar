package com.bookcalendar.demo.repository;

import com.bookcalendar.demo.domain.Event;
import com.bookcalendar.demo.dto.EventDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    List<Event> findByCalendarId(Long calendarId);

    @Query("select new com.bookcalendar.demo.dto.EventDto(e.id,e.inventoryBook.id, e.inventoryBook.book.title,e.startTime, e.endTime)" +
            " from Event e" +
            " where e.calendar.id = ?1 ")
    List<EventDto> getDtoByCalendarId(Long calendarId);
}

