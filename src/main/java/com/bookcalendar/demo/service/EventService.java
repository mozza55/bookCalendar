package com.bookcalendar.demo.service;

import com.bookcalendar.demo.domain.Event;
import com.bookcalendar.demo.repository.EventRepository;
import com.bookcalendar.demo.repository.InventoryBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final InventoryBookRepository inventoryBookRepository;
    private final EventRepository eventRepository;

    public Long saveEventWithUpate(Event event, Long inventoryBookId, int currentPage){
        Long saveId = eventRepository.save(event).getId();
        inventoryBookRepository.updateCurrentPage(inventoryBookId,currentPage);
        return saveId;
    }
}
