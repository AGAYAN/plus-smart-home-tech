package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.delivery.Dto.DeliveryDto;
import ru.yandex.practicum.delivery.controller.DeliveryClient;
import ru.yandex.practicum.payment.Dto.OrderDto;
import ru.yandex.practicum.service.DeliveryService;

@RestController
@RequiredArgsConstructor
public class DeliveryController implements DeliveryClient {

    private final DeliveryService deliveryService;

    @Override
    public ResponseEntity<DeliveryDto> createDelivery(DeliveryDto deliveryDto) {
        deliveryService.createDelivery(deliveryDto);
        return ResponseEntity.ok(deliveryDto);
    }

    @Override
    public ResponseEntity<Void> successfulDelivery(String deliveryId) {
        deliveryService.successfulDelivery(deliveryId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deliveryPicked(String deliveryId) {
        deliveryService.ReceivingTheProduct(deliveryId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deliveryFailed(String deliveryId) {
        deliveryService.DeliveryFaled(deliveryId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Double> DeliveryCost(OrderDto orderDto) {
       deliveryService.DeliveryCost(orderDto);
       return ResponseEntity.ok().build();
    }
}
