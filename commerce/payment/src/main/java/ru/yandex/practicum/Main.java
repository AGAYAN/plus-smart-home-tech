package ru.yandex.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.yandex.practicum.delivery.controller.DeliveryClient;
import ru.yandex.practicum.order.controller.OrderClient;

@SpringBootApplication
@EnableEurekaServer // or client
@EnableFeignClients(clients = {DeliveryClient.class, OrderClient.class})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}