package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.order.Dto.CreateNewOrderRequest;
import ru.yandex.practicum.order.Dto.ProductReturnRequest;
import ru.yandex.practicum.order.controller.OrderClient;
import ru.yandex.practicum.payment.Dto.OrderDto;
import ru.yandex.practicum.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController implements OrderClient{

    private OrderService orderService;

    @Override
    public List<OrderDto> getOrders(String username) {
        return orderService.getUserOrders(username);
    }

    @Override
    public OrderDto asscemblyOrder(String orderId) {
        return orderService.asscemblyOrder(orderId);
    }

    @Override
    public OrderDto asscemblyOrderFailed(String orderId) {
        return orderService.asscemblyOrderFailed(orderId);
    }

    @Override
    public OrderDto createOrders(CreateNewOrderRequest username) {
        return orderService.createOrder(username);
    }

    @Override
    public OrderDto deliveryOrderFailed(String orderId) {
        return orderService.deliveryOrderFailed(orderId);
    }

    @Override
    public OrderDto deliveryOrder(String orderId) {
        return orderService.deliveryOrder(orderId);
    }

    @Override
    public OrderDto paymentRefund(String orderId) {
        return orderService.paymentRefund(orderId);
    }

    @Override
    public OrderDto paymentOrderFailed(String orderId) {
        return orderService.paymentOrderFailed(orderId);
    }

    @Override
    public OrderDto orderCompleted(String orderId) {
        return orderService.orderCompleted(orderId);
    }

    @Override
    public OrderDto returnOrder(ProductReturnRequest request) {
        return orderService.returnOrder(request);
    }

    @Override
    public OrderDto calculateTotalOrder(String orderId) {
        return orderService.calculateTotalOrder(orderId);
    }

    @Override
    public OrderDto calculateDeliveryOrder(String orderId) {
        return orderService.calculateTotalOrder(orderId);
    }


}
