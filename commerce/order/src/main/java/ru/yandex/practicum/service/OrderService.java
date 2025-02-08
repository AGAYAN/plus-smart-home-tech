package ru.yandex.practicum.service;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.order.Dto.CreateNewOrderRequest;
import ru.yandex.practicum.order.Dto.ProductReturnRequest;
import ru.yandex.practicum.payment.Dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getUserOrders(String username);

    OrderDto createOrder(CreateNewOrderRequest createNewOrderRequest);

    OrderDto asscemblyOrder(String orderId);

    OrderDto deliveryOrderFailed(String orderId);

    OrderDto deliveryOrder(String orderId);

    OrderDto paymentRefund(String orderId);

    OrderDto paymentOrderFailed(String orderId);

    OrderDto orderCompleted(String orderId);

    OrderDto asscemblyOrderFailed(String orderId);

    OrderDto returnOrder(ProductReturnRequest request);

    OrderDto calculateTotalOrder(String orderId);
}
