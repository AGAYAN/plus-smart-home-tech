package ru.yandex.practicum.mapper;


import org.mapstruct.Mapper;
import ru.yandex.practicum.dto.DimensionDto;
import ru.yandex.practicum.dto.NewProductInWarehouseDto;
import ru.yandex.practicum.model.WarehouseProduct;

import java.awt.*;

@Mapper
public interface WarehouseMapper {
    WarehouseProduct toWarehouse(NewProductInWarehouseDto request);

    Dimension toDimension(DimensionDto dimensionDto);
}
