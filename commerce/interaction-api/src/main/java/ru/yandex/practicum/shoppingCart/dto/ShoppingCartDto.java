package ru.yandex.practicum.shoppingCart.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ShoppingCartDto {
    private final String message;
    private final Map<Long, Integer> products;
}
