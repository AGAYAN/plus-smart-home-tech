package ru.yandex.practicum.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewProductInWarehouseDto {
    private String productId;

    private boolean fragile;

    private DimensionDto dimension;

    private double weight;
}
