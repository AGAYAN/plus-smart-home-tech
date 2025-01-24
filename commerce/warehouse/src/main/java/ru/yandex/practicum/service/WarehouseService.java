package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.NewProductInWarehouseDto;
import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;

public interface WarehouseService {

    BookedProductsDto checkAvailableProducts(ShoppingCartDto shoppingCartDto);

    void createProductToWarehouse(NewProductInWarehouseDto request);
}
