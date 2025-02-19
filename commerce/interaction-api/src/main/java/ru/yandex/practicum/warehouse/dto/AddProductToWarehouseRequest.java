package ru.yandex.practicum.warehouse.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AddProductToWarehouseRequest {
    private Long productId;
    @Min(1)
    private Long quantity;
}
