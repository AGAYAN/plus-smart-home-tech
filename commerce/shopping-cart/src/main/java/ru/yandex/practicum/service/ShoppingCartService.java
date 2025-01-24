package ru.yandex.practicum.service;

import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;
import ru.yandex.practicum.shoppingCart.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;
import ru.yandex.practicum.model.ShoppingCart;

import java.util.Map;

public interface ShoppingCartService {
    ShoppingCartDto addProductToCart(String username, Map<String, Integer> products);

    ShoppingCartDto getShoppingCart(String username);

    void deactivateShoppingCart(String username);

    ShoppingCart updateCart(String username, Map<String, Integer> products);

    ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest request);

    BookedProductsDto reserveItems(String username);
}
