package ru.yandex.practicum.mapper;


import org.mapstruct.Mapper;
import ru.yandex.practicum.dto.NewProductInWarehouseDto;
import ru.yandex.practicum.model.WarehouseProduct;

@Mapper
public interface WarehouseMapper {
    WarehouseProduct toWarehouse(NewProductInWarehouseDto request);
}
