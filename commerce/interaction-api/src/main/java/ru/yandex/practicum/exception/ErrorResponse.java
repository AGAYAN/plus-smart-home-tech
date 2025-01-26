package ru.yandex.practicum.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {
    private String message;
    private String details;

    public ErrorResponse(String s, String message) {
        this.details = s;
        this.message = message;
    }
}
