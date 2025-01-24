package ru.yandex.practicum.shoppingStore.dto;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.shoppingStore.enums.ProductCategory;
import ru.yandex.practicum.shoppingStore.enums.ProductState;
import ru.yandex.practicum.shoppingStore.enums.QuantityState;

@Getter
@Setter
public class ProductDto {
    private Long productId;
    private String productName;
    private String description;
    private String imageSrc;
    private QuantityState quantityState;
    private ProductState productState;
    private Double rating;
    private ProductCategory productCategory;
    private Double price;

}
