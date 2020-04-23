package com.bookcalendar.demo.controller;

import com.bookcalendar.demo.domain.Event;
import com.bookcalendar.demo.domain.InventoryBook;
import com.bookcalendar.demo.domain.Member;
import com.bookcalendar.demo.dto.EventDto;
import com.bookcalendar.demo.repository.EventRepository;
import com.bookcalendar.demo.repository.InventoryBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final InventoryBookRepository inventoryBookRepository;
    private final EventRepository eventRepository;

    @GetMapping("/calendar/{calendarId}")
    public String getCalendar(@PathVariable Long calendarId, HttpSession session, Model model){
        Member member =(Member) session.getAttribute("member");
        List<InventoryBook> inventoryBookList = inventoryBookRepository.findByInventoryIdWithBook(member.getInventory().getId());
        model.addAttribute("bookList",inventoryBookList);
        List<Event> eventList = eventRepository.findByCalendarId(calendarId);

        log.info("calendarid: "+calendarId);
        log.info("eventList Size :"+eventList.size());
        return "members/calendar";
    }


    @GetMapping("/calendar/{calendarId}/events")
    @ResponseBody
    public List<EventDto> getEventList(@PathVariable Long calendarId){
        return eventRepository.getDtoByCalendarId(calendarId);

    }
    @PostMapping("/calendar/{calendarId}/events")
    @ResponseBody
    public String createEvent(@PathVariable Long calendarId,
                              HttpSession session,
                              @RequestParam Long inventoryBookId,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")LocalDateTime start,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")LocalDateTime end){
        log.info("calendarid: "+calendarId);
        Member member =(Member) session.getAttribute("member");

        Event event = Event.createEvent(member.getCalendar(),
                inventoryBookRepository.getOne(inventoryBookId),
                date, start,end);
        eventRepository.save(event);
        return event.getId().toString();
    }

    @DeleteMapping("/calendar/{calendarId}/events/{eventId}")
    @ResponseBody
    public ResponseEntity<String> deleteEvent(@PathVariable Long calendarId,
                                      @PathVariable Long eventId){
        log.info("eventId:" +eventId);
        eventRepository.deleteById(eventId);
        return new ResponseEntity<>("sucess", HttpStatus.OK);
    }
}
