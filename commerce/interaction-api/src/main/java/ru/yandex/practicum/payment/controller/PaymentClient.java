package ru.yandex.practicum.payment.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yandex.practicum.payment.Dto.OrderDto;
import ru.yandex.practicum.payment.Dto.PaymentDto;
import ru.yandex.practicum.payment.enums.OrderState;

import java.math.BigDecimal;

@FeignClient(name = "payment", path = "/api/v1/payment")
public interface PaymentClient {

    @PostMapping
    PaymentDto createPayment(OrderDto orderDto);

    @PostMapping("/totalCost")
    BigDecimal totalCost(OrderDto orderDto);

    @PostMapping("/refund")
    void paymendRefund(String paymendId);

    @PostMapping("/failed")
    void paymendFailed(String paymentId);

    @PostMapping("/productCost")
    BigDecimal calculateProductCost(OrderDto order);
}
