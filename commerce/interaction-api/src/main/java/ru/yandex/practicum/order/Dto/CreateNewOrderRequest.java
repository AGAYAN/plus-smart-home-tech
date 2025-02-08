package ru.yandex.practicum.order.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateNewOrderRequest {
    ShoppingCartDto shoppingCart;
    AddressDto deliveryAddress;
}
