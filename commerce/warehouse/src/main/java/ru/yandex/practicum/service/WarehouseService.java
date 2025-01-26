package ru.yandex.practicum.service;

import ru.yandex.practicum.warehouse.dto.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.dto.AddressDto;
import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseDto;

public interface WarehouseService {

    BookedProductsDto checkAvailableProducts(ShoppingCartDto shoppingCartDto);

    void createProductToWarehouse(NewProductInWarehouseDto request);

    AddressDto addAddress();

    void increaseProductQuantity(AddProductToWarehouseRequest request);
}
