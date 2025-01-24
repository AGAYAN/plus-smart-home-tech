package ru.yandex.practicum.warehouse.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DimensionDto {
    private double width;

    private double height;

    private double depth;
}
