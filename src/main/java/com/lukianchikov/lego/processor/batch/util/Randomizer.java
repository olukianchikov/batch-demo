package com.lukianchikov.lego.processor.batch.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Randomizer {

    public String generateUuidString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
