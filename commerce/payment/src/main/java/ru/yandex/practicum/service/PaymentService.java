package ru.yandex.practicum.service;

import ru.yandex.practicum.payment.Dto.OrderDto;
import ru.yandex.practicum.payment.Dto.PaymentDto;

import java.math.BigDecimal;

public interface PaymentService {
    PaymentDto createPayment(OrderDto orderDto);

    BigDecimal totalCost(OrderDto orderDto);

    void paymendRefund(String paymendId);

    void paymendFailed(String paymentId);
}
