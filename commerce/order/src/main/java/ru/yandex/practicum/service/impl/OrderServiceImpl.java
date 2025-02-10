package ru.yandex.practicum.service.impl;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.delivery.enums.DeliveryState;
import ru.yandex.practicum.delivery.Dto.DeliveryDto;
import ru.yandex.practicum.delivery.controller.DeliveryClient;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.order.Dto.CreateNewOrderRequest;
import ru.yandex.practicum.order.Dto.ProductReturnRequest;
import ru.yandex.practicum.payment.Dto.OrderDto;
import ru.yandex.practicum.payment.controller.PaymentClient;
import ru.yandex.practicum.payment.enums.OrderState;
import ru.yandex.practicum.repository.OrderRepository;
import ru.yandex.practicum.service.OrderService;
import ru.yandex.practicum.shoppingCart.controller.ShoppingCartClient;
import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;
import ru.yandex.practicum.warehouse.controller.WarehouseClient;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private ShoppingCartClient cartClient;
    private OrderMapper orderMapper;
    private WarehouseClient warehouseClient;
    private DeliveryClient deliveryClient;
    private PaymentClient paymentClient;

    @Override
    public List<OrderDto> getUserOrders(String username) {
        ShoppingCartDto shoppingCart = cartClient.getShoppingCart(username).getBody();
        return orderMapper.mapListOrders(orderRepository.findByShoppingCartId(shoppingCart.getMessage()));
    }

    @Override
    public OrderDto createOrder(CreateNewOrderRequest createNewOrderRequest) {
        Order order = new Order();
        order.setShoppingCartId((createNewOrderRequest.getShoppingCart().getMessage()));
        order.setProducts(createNewOrderRequest.getShoppingCart().getProducts());
        order.setState(OrderState.NEW);
        orderRepository.save(order);

        ProductReturnRequest assemblyRequest = new ProductReturnRequest(
                order.getOrderId(),
                createNewOrderRequest.getShoppingCart().getProducts()
        );

        BookedProductsDto bookedProductsDto = warehouseClient.assemblyProducts(assemblyRequest).getBody();

        order.setDeliveryVolume(bookedProductsDto.getDeliveryVolume());
        order.setDeliveryWeight(bookedProductsDto.getDeliveryWeight());
        order.setFragile(bookedProductsDto.isFragile());

        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setOrderId(order.getOrderId());
        deliveryDto.setFromAddress(createNewOrderRequest.getDeliveryAddress());
        deliveryDto.setToAddress(createNewOrderRequest.getDeliveryAddress());
        deliveryDto.setDeliveryState(DeliveryState.CREATED);

        DeliveryDto createdDelivery = deliveryClient.createDelivery(deliveryDto).getBody();
        order.setDeliveryId(createdDelivery.getDeliveryId());

        orderRepository.save(order);
        return order;
    }

    @Override
    public OrderDto asscemblyOrder(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty())
            throw new RuntimeException("Нету заказа с таким id");

        Order order = optionalOrder.get();
        order.setState(OrderState.ASSEMBLED);
        orderRepository.save(order);
        return orderMapper.OrderDtoToOrder(order);
    }

    @Override
    public OrderDto deliveryOrderFailed(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty())
            throw new RuntimeException("Нету заказа с таким id");

        Order order = optionalOrder.get();
        order.setState(OrderState.DELIVERY_FAILED);
        orderRepository.save(order);
        return orderMapper.OrderDtoToOrder(order);
    }

    @Override
    public OrderDto deliveryOrder(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty())
            throw new RuntimeException("Нету заказа с таким id");

        Order order = optionalOrder.get();
        order.setState(OrderState.DELIVERED);
        orderRepository.save(order);
        return orderMapper.OrderDtoToOrder(order);
    }

    @Override
    public OrderDto paymentRefund(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty())
            throw new RuntimeException("Нету заказа с таким id");

        Order order = optionalOrder.get();
        order.setState(OrderState.PAID);
        orderRepository.save(order);
        return orderMapper.OrderDtoToOrder(order);
    }

    @Override
    public OrderDto paymentOrderFailed(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty())
            throw new RuntimeException("Нету заказа с таким id");

        Order order = optionalOrder.get();
        order.setState(OrderState.PAYMENT_FAILED);
        orderRepository.save(order);
        return orderMapper.OrderDtoToOrder(order);
    }

    @Override
    public OrderDto orderCompleted(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty())
            throw new RuntimeException("Нету заказа с таким id");

        Order order = optionalOrder.get();
        order.setState(OrderState.COMPLETED);
        orderRepository.save(order);
        return orderMapper.OrderDtoToOrder(order);
    }

    @Override
    public OrderDto asscemblyOrderFailed(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty())
            throw new RuntimeException("Нету заказа с таким id");

        Order order = optionalOrder.get();
        order.setState(OrderState.ASSEMBLY_FAILED);
        orderRepository.save(order);
        return orderMapper.OrderDtoToOrder(order);
    }

    @Override
    public OrderDto returnOrder(ProductReturnRequest request) {
        Optional<Order> optionalOrder = orderRepository.findById(request.getOrderId());
        if (optionalOrder.isEmpty())
            throw new RuntimeException("Нету заказа с таким id");

        Order order = optionalOrder.get();
        order.setState(OrderState.PRODUCT_RETURNED);
        orderRepository.save(order);
        return orderMapper.OrderDtoToOrder(order);
    }

    @Override
    public OrderDto configDelivery(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty())
            throw new RuntimeException("Нету заказа с таким id");

        Order order = optionalOrder.get();
        order.setState(OrderState.DELIVERED);
        orderRepository.save(order);
        return orderMapper.OrderDtoToOrder(order);
    }

    @Override
    public OrderDto calculateTotalOrder(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty())
            throw new RuntimeException("Нету заказа с таким id");

        Order order = optionalOrder.get();
        order.setState(OrderState.ON_PAYMENT);
        orderRepository.save(order);
        return orderMapper.OrderDtoToOrder(order);
    }


}
