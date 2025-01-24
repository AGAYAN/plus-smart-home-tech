package ru.yandex.practicum.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.shoppingStore.enums.ProductCategory;
import ru.yandex.practicum.shoppingStore.enums.ProductState;
import ru.yandex.practicum.shoppingStore.enums.QuantityState;


@Getter
@Setter
@Entity
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String description;
    private String imageSrc;
    private QuantityState quantityState;
    private ProductState productState;
    private Double rating;
    private ProductCategory productCategory;
    private Double price;
    //private int quantity;

}
