package ru.yandex.practicum.payment.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.payment.enums.OrderState;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderDto {
    private Long orderId;
    private Long shoppingCartId;
    private Map<Long, Integer> products;
    private Long paymentId;
    private Long deliveryId;
    private OrderState state;
    private double deliveryWeight;
    private double deliveryVolume;
    private boolean fragile;
    private Double totalPrice;
    private Double deliveryPrice;
    private Double productPrice;

}
