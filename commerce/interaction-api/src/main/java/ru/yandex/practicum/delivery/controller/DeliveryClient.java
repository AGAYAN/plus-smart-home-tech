package ru.yandex.practicum.delivery.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.yandex.practicum.delivery.Dto.DeliveryDto;
import ru.yandex.practicum.payment.Dto.OrderDto;

@FeignClient(name = "delivery", path = "/api/v1/delivery")
public interface DeliveryClient {

    @PutMapping()
    ResponseEntity<DeliveryDto> createDelivery(DeliveryDto deliveryDto);

    @PostMapping("/successful")
    ResponseEntity<Void> successfulDelivery(String deliveryId);

    @PostMapping("/picked")
    ResponseEntity<Void> deliveryPicked(String deliveryId);

    @PostMapping("/failed")
    ResponseEntity<Void> deliveryFailed(String deliveryId);

    @PostMapping("/cost")
    ResponseEntity<Double> DeliveryCost(OrderDto orderDto);
}
