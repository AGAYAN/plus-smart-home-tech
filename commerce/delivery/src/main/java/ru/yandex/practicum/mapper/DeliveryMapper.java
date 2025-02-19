package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.delivery.Dto.DeliveryDto;
import ru.yandex.practicum.model.Delivery;

@Mapper
public interface DeliveryMapper {
    Delivery deliveryDtoToDelivery(DeliveryDto deliveryDto);

    DeliveryDto deliveryToDeliveryDto(Delivery delivery);
}
