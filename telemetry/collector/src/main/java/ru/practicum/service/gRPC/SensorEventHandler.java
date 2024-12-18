package ru.practicum.service.gRPC;


import ru.yandex.practicum.grpc.collector_controller.proto.SensorEventProto;

public interface SensorEventHandler {
    SensorEventProto.PayloadCase getMessageType();

    void handle(SensorEventProto event);
}
