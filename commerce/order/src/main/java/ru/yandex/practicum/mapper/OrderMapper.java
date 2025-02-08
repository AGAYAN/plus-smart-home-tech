package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.payment.Dto.OrderDto;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<OrderDto> mapListOrders(List<Order> orders);

    OrderDto OrderDtoToOrder(Order order);
}
