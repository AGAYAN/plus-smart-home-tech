package ru.practicum.service.gRPC.servicegRPC;

import org.springframework.stereotype.Component;
import ru.practicum.service.gRPC.SensorEventHandler;
import ru.yandex.practicum.grpc.collector_controller.proto.MotionSensorEvent;
import ru.yandex.practicum.grpc.collector_controller.proto.SensorEventProto;

@Component
public class MotionSensorEventHandler implements SensorEventHandler {
    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto event) {
        if (event.hasMotionSensorEvent()) {
            MotionSensorEvent motionEvent = event.getMotionSensorEvent();
            System.out.println("Обработано событие переключателя: motion=" + motionEvent.getMotion());
        } else {
            System.out.println("Получено неизвестное событие.");
        }
    }
}
