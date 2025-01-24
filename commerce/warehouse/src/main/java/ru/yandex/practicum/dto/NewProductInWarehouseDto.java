package ru.yandex.practicum.dto;

import lombok.*;

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
