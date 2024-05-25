package com.bloggApp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String message;

    private boolean success;

    private LocalDate date;

    private LocalTime time;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }
}
