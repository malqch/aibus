package com.wntime.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ScoreMaxHundredSerialize extends JsonSerializer<Float> {
    @Override
    public void serialize(Float aFloat, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(aFloat.floatValue()<-100f?-100f:aFloat);
    }
}
