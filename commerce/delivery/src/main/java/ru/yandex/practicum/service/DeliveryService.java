package ru.yandex.practicum.service;

import org.springframework.core.annotation.Order;
import ru.yandex.practicum.delivery.Dto.DeliveryDto;
import ru.yandex.practicum.model.Delivery;
import ru.yandex.practicum.payment.Dto.OrderDto;

public interface DeliveryService {

    DeliveryDto createDelivery(DeliveryDto dto);

    Delivery successfulDelivery(String deliveryId);

    Delivery ReceivingTheProduct(String deliveryId);

    Delivery DeliveryFaled(String deliveryId);

    double DeliveryCost(OrderDto orderDto);
}
