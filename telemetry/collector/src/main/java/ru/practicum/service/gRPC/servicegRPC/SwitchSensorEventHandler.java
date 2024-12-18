package ru.practicum.service.gRPC.servicegRPC;

import org.springframework.stereotype.Component;
import ru.practicum.service.gRPC.SensorEventHandler;
import ru.yandex.practicum.grpc.collector_controller.proto.SensorEventProto;
import ru.yandex.practicum.grpc.collector_controller.proto.SwitchSensorEvent;

@Component
public class SwitchSensorEventHandler implements SensorEventHandler {

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto event) {
        if (event.hasSwitchSensorEvent()) {
            SwitchSensorEvent switchEvent = event.getSwitchSensorEvent();
            System.out.println("Обработано событие переключателя: state=" + switchEvent.getState());
        } else {
            System.out.println("Получено неизвестное событие.");
        }
    }
}

