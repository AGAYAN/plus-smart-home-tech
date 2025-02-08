package ru.yandex.practicum.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.order.controller.OrderClient;
import ru.yandex.practicum.payment.Dto.OrderDto;
import ru.yandex.practicum.payment.Dto.PaymentDto;
import ru.yandex.practicum.repository.PaymentRepository;
import ru.yandex.practicum.service.PaymentService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderClient orderClient;

    @Override
    public PaymentDto createPayment(OrderDto orderDto) {
        Payment payment = new Payment();
        payment.setPaymentId(orderDto.getPaymentId().toString());
        payment.setTotalPayment(orderDto.getTotalPrice());
        payment.setDeliveryTotal(orderDto.getDeliveryPrice());
        payment.setFeeTotal(orderDto.getTotalPrice());
        paymentRepository.save(payment);
        return paymentMapper.PaymentDtoToPayment(payment);
    }

    @Override
    public double totalCost(OrderDto orderDto) {
        double productPrice = orderDto.getProductPrice();
        double deliveryPrice = orderDto.getDeliveryPrice();
        double tax = productPrice * 0.1;
        double totalCost = productPrice + tax + deliveryPrice;

        return totalCost;
    }

    @Override
    public void paymendRefund(String paymentId) {
        Optional<Payment> optionalDelivery = paymentRepository.findById(paymentId);
        if (optionalDelivery.isEmpty()) {
            throw new RuntimeException("Не сушествует такого id");
        }

        Payment payment = optionalDelivery.get();
        orderClient.paymentRefund(payment.getOrderId());
        paymentRepository.save(payment);
    }

    @Override
    public void paymendFailed(String paymentId) {
        Optional<Payment> optionalDelivery = paymentRepository.findById(paymentId);
        if (optionalDelivery.isEmpty()) {
            throw new RuntimeException("Не сушествует такого id");
        }

        Payment payment = optionalDelivery.get();
        orderClient.deliveryOrderFailed(payment.getOrderId());
        paymentRepository.save(payment);
    }
}
