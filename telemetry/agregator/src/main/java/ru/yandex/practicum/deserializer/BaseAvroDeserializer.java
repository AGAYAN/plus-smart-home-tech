package ru.yandex.practicum.deserializer;


import lombok.RequiredArgsConstructor;
import org.apache.avro.Schema;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RequiredArgsConstructor
public class BaseAvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {

    private final DecoderFactory decoderFactory = DecoderFactory.get();
    private final Schema schema;

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) return null;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data)) {
            DatumReader<T> datumReader = new SpecificDatumReader<>(schema);
            BinaryDecoder decoder = decoderFactory.binaryDecoder(inputStream, null);
            return datumReader.read(null, decoder);
        } catch (IOException e) {
            throw new SerializationException("Error deserializing Avro message", e);
        }
    }

    @Override
    public void close() {}
}