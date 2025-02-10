package ru.yandex.practicum.order.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.order.Dto.CreateNewOrderRequest;
import ru.yandex.practicum.order.Dto.ProductReturnRequest;
import ru.yandex.practicum.payment.Dto.OrderDto;

import java.util.List;

@FeignClient(name = "order", path = "/api/v1/order")
public interface OrderClient {

    @GetMapping
    List<OrderDto> getOrders(String username);

    @PutMapping
    OrderDto createOrders(@RequestBody CreateNewOrderRequest username);

    @PostMapping("/delivery")
    OrderDto confirmDelivery(@RequestBody String orderId);

    @PostMapping("/assembly")
    OrderDto asscemblyOrder(@RequestBody String orderId);

    @PostMapping("/assembly/failed")
    OrderDto asscemblyOrderFailed(@RequestBody String orderId);

    @PostMapping("/delivery/failed")
    OrderDto deliveryOrderFailed(@RequestBody String orderId);

    @PostMapping("/failed")
    OrderDto deliveryOrder(@RequestBody String orderId);

    @PostMapping("/payment")
    OrderDto paymentRefund(@RequestBody String orderId);

    @PostMapping("/payment/failed")
    OrderDto paymentOrderFailed(@RequestBody String orderId);

    @PostMapping("/completed")
    OrderDto orderCompleted(@RequestBody String orderId);

    @PostMapping("/return")
    OrderDto returnOrder(@RequestBody ProductReturnRequest request);

    @PostMapping("/calculate/total")
    OrderDto calculateTotalOrder(@RequestBody String orderId);

    @PostMapping("/calculate/delivery")
    OrderDto calculateDeliveryOrder(@RequestBody String orderId);
}
