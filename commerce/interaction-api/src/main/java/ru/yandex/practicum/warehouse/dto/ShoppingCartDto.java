package ru.yandex.practicum.warehouse.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ShoppingCartDto {
    private Long shoppingCartId;
    private Map<String, Long> products;
}
