package com.lukianchikov.lego.processor.batch.writable;

import com.lukianchikov.lego.processor.batch.destination.Destination;
import com.lukianchikov.lego.processor.batch.model.Price;
import com.lukianchikov.lego.processor.batch.util.Randomizer;
import com.lukianchikov.lego.processor.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceTargetPathMaker implements TargetPathMaker<Price> {

    private final Randomizer randomizer;
    private final AppProperties appProperties;


    @Autowired
    public PriceTargetPathMaker(Randomizer randomizer, AppProperties appProperties) {
        this.randomizer = randomizer;
        this.appProperties = appProperties;
    }

    @Override
    public String getTargetPath() {
        String containerPath = this.getTargetContainer();
        String suffix = randomizer.generateUuidString();
        String objectName = String.format("price-%s.avro", suffix);
        return String.format("%s/%s", containerPath, objectName);
    }

    @Override
    public Destination getTargetDestination() {
        return Destination.of(appProperties);
    }

    @Override
    public String getTargetContainer() {
        Destination targetDestination = this.getTargetDestination();
        return targetDestination.getTargetContainerPath("prices");
    }
}
