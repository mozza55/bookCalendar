package com.bookcalendar.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class EventDto {
    private String id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private ExtendedProps extendedProps;

    public EventDto(Long id,Long inventoryBookId, String title, LocalDateTime start, LocalDateTime end) {
        this.id = id.toString();
        this.title = title;
        this.start = start;
        this.end = end;
        this.extendedProps = new ExtendedProps(inventoryBookId);
    }
    @Data
    public class ExtendedProps{
        Long  inventoryBookId;
        int page;

        public ExtendedProps(Long inventoryBookId) {
            this.inventoryBookId = inventoryBookId;
            this.page=0;
        }
    }
}
