package ru.yandex.practicum.mapper;


import org.mapstruct.Mapper;

import ru.yandex.practicum.model.WarehouseProduct;
import ru.yandex.practicum.warehouse.dto.DimensionDto;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseDto;

import java.awt.*;

@Mapper
public interface WarehouseMapper {
    WarehouseProduct toWarehouse(NewProductInWarehouseDto request);

    Dimension toDimension(DimensionDto dimensionDto);
}
