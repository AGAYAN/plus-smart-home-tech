package ru.yandex.practicum.shoppingCart.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ChangeProductQuantityRequest {
    private Long productId;
    private int newQuantity;
}
