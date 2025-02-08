package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.payment.Dto.OrderDto;
import ru.yandex.practicum.payment.Dto.PaymentDto;
import ru.yandex.practicum.payment.controller.PaymentClient;
import ru.yandex.practicum.payment.enums.OrderState;
import ru.yandex.practicum.service.PaymentService;

@Service
@RequiredArgsConstructor
public class PaymentController implements PaymentClient {

    private final PaymentService paymentService;

    @Override
    public PaymentDto createPayment(OrderDto orderDto) {
        return paymentService.createPayment(orderDto);
    }

    @Override
    public OrderState totalCost(OrderDto orderDto) {
        return paymentService.totalCost(orderDto);
    }

    @Override
    public void paymendFailed(String paymentId) {
        paymentService.paymendFailed(paymentId);
    }

    @Override
    public void paymendRefund(String paymentId) {
        paymentService.paymendFailed(paymentId);
    }

}
