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
    private String orderId;
    private String shoppingCartId;
    private Map<Long, Integer> products;
    private String paymentId;
    private String deliveryId;
    private OrderState state;
    private double deliveryWeight;
    private double deliveryVolume;
    private boolean fragile;
    private BigDecimal totalPrice;
    private BigDecimal deliveryPrice;
    private BigDecimal productPrice;

}
