package ru.yandex.practicum.order.Dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReturnRequest {
    private String orderId;
    private Map<Long, Integer> products;
}
