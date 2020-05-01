package com.bookcalendar.demo.dto;



import com.bookcalendar.demo.domain.ReadStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class InventoryBookDto {
    private Long id;
    private Long bookId;
    private ReadStatus status;
    private int currentPage;
    private int page;
    private LocalDate addDate;
    private LocalDate readDate;
    private int rating;
    private String comment;

    public InventoryBookDto(Long id, Long bookId, ReadStatus status, int currentPage, int page, LocalDate addDate, LocalDate readDate, int rating, String comment) {
        this.id = id;
        this.bookId = bookId;
        this.status = status;
        this.currentPage = currentPage;
        this.page = page;
        this.addDate = addDate;
        this.readDate = readDate;
        this.rating = rating;
        this.comment = comment;
    }
}
