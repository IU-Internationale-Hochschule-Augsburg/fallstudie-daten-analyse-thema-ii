package com.surveymaster.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Entity
@Table
@Data
public class Survey {
    private Long userId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long surveyId;

    @Column(length = 1024)
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Column(length = 1024)
    private String description;

    public Survey() {
    }

    public Survey(Long userId, String title, LocalDate startDate, LocalDate endDate, String description) {
        this.userId = userId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }
}
