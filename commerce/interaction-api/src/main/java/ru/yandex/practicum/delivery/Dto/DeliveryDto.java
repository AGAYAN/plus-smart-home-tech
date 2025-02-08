package ru.yandex.practicum.delivery.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.delivery.enums.DeliveryState;
import ru.yandex.practicum.order.Dto.AddressDto;

@Getter
@Setter
@RequiredArgsConstructor
public class DeliveryDto {
    private String deliveryId;
    private AddressDto fromAddress;
    private AddressDto toAddress;
    private String orderId;
    private DeliveryState deliveryState;
}
