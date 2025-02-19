package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.delivery.enums.DeliveryState;
import ru.yandex.practicum.delivery.Dto.DeliveryDto;
import ru.yandex.practicum.mapper.DeliveryMapper;
import ru.yandex.practicum.model.Delivery;
import ru.yandex.practicum.order.Dto.AddressDto;
import ru.yandex.practicum.order.controller.OrderClient;
import ru.yandex.practicum.payment.Dto.OrderDto;
import ru.yandex.practicum.repository.DeliveryRepository;
import ru.yandex.practicum.service.DeliveryService;
import ru.yandex.practicum.warehouse.controller.WarehouseClient;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private DeliveryRepository deliveryRepository;
    private DeliveryMapper deliveryMapper;
    private OrderClient orderClient;
    private WarehouseClient warehouseClient;

    @Override
    public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = deliveryMapper.deliveryDtoToDelivery(deliveryDto);
        delivery.setDeliveryState(DeliveryState.CREATED);
        deliveryRepository.save(delivery);
        return deliveryMapper.deliveryToDeliveryDto(delivery);
    }

    @Override
    public Delivery successfulDelivery(String deliveryId) {
        Optional<Delivery> optionalDelivery = deliveryRepository.findById(deliveryId);
        if (optionalDelivery.isEmpty()) {
            throw new RuntimeException("Не сушествует такого id");
        }

        Delivery delivery = optionalDelivery.get();
        orderClient.orderCompleted(delivery.getOrderId());
        delivery.setDeliveryState(DeliveryState.DELIVERED);
        deliveryRepository.save(delivery);
        return delivery;
    }

    @Override
    public Delivery ReceivingTheProduct(String deliveryId) {
        Optional<Delivery> optionalDelivery = deliveryRepository.findById(deliveryId);
        if (optionalDelivery.isEmpty()) {
            throw new RuntimeException("Не сушествует такого id");
        }

        Delivery delivery = optionalDelivery.get();
        orderClient.asscemblyOrder(delivery.getDeliveryId());
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        deliveryRepository.save(delivery);
        return delivery;
    }

    @Override
    public Delivery DeliveryFaled(String deliveryId) {
        Optional<Delivery> optionalDelivery = deliveryRepository.findById(deliveryId);
        if (optionalDelivery.isEmpty()) {
            throw new RuntimeException("Не сушествует такого id");
        }

        Delivery delivery = optionalDelivery.get();
        orderClient.deliveryOrderFailed(deliveryId);
        delivery.setDeliveryState(DeliveryState.FAILED);
        deliveryRepository.save(delivery);
        return delivery;
    }

    @Override
    public double DeliveryCost(OrderDto orderDto) {
        Delivery delivery = deliveryRepository.findById(String.valueOf(orderDto.getDeliveryId()))
                .orElseThrow(() -> new RuntimeException("Не существует доставки с id = " + orderDto.getDeliveryId()));

        AddressDto warehouseAddr = warehouseClient.getAddress().getBody();
        double baseCost = 5.0;
        double warehouseMultiplier;
        if (warehouseAddr.getCity().equalsIgnoreCase("ADDRESS_2")) {
            warehouseMultiplier = 2.0;
        } else {
            warehouseMultiplier = 1.0;
        }
        double cost = baseCost * warehouseMultiplier + baseCost;

        if (orderDto.isFragile()) {
            cost += cost * 0.2;
        }

        cost += orderDto.getDeliveryWeight() * 0.3;
        cost += orderDto.getDeliveryVolume() * 0.2;

        if (!warehouseAddr.getStreet().equalsIgnoreCase(delivery.getToAddress().getStreet())) {
            cost += cost * 0.2;
        }

        return cost;
    }


}
