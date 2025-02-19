package ru.yandex.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.yandex.practicum.order.controller.OrderClient;
import ru.yandex.practicum.payment.controller.PaymentClient;

@SpringBootApplication
@EnableEurekaServer // or client
@EnableFeignClients(clients = {OrderClient.class, PaymentClient.class})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}