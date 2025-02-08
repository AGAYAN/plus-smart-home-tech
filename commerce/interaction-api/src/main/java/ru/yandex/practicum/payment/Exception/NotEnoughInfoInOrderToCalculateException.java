package ru.yandex.practicum.payment.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class NotEnoughInfoInOrderToCalculateException extends RuntimeException {
    private String userMessage;
    private String httpStatus;
}