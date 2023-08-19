package com.lukianchikov.lego.processor.batch.listener;

import com.lukianchikov.lego.processor.batch.model.Price;
import com.lukianchikov.lego.processor.batch.model.PriceIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

/**
 * A listener that logs any skips in processing of Price input records.
 */
public class PriceStepSkipListener implements SkipListener<PriceIn, Price> {

    private static final Logger log = LoggerFactory.getLogger(PriceStepSkipListener.class);

    @Override
    public void onSkipInRead(Throwable t) {
        log.warn(String.format("Failed to read a price item: %s", t.getMessage()));
    }

    @Override
    public void onSkipInWrite(Price item, Throwable t) {
        log.warn(String.format("Failed to write a price item { %s }: %s",
                item.toString(),
                t.getMessage()));
    }

    @Override
    public void onSkipInProcess(PriceIn item, Throwable t) {
        log.warn(String.format("Failed to read a price item { %s }: %s. Check if the record is malformed.",
                item.toString(),
                t.getMessage()));
    }

}
