package ru.practicum.serializer;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Serializer;


import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class SerializerAvro implements Serializer<SpecificRecordBase> {

    @Override
    public byte[] serialize(String topic, SpecificRecordBase data) {
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            BinaryEncoder binaryEncoder = null;
            binaryEncoder = EncoderFactory.get().binaryEncoder(outputStream, binaryEncoder);

            DatumWriter<SpecificRecordBase> writer = new SpecificDatumWriter<>(data.getSchema());

            writer.write(data, binaryEncoder);

            binaryEncoder.flush();

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
