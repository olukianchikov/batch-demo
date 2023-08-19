package com.lukianchikov.lego.processor.batch.listener;

import com.lukianchikov.lego.processor.batch.model.Product;
import com.lukianchikov.lego.processor.batch.model.ProductIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;


/**
 * A listener that logs any skips in processing of Product input records.
 */
public class ProductStepSkipListener implements SkipListener<ProductIn, Product> {

    private static final Logger log = LoggerFactory.getLogger(ProductStepSkipListener.class);

    @Override
    public void onSkipInRead(Throwable t) {
        log.warn(String.format("Failed to read a product item: %s; cause: %s", t.getMessage(), t.getCause().getMessage()));
    }

    @Override
    public void onSkipInWrite(Product item, Throwable t) {
        log.warn(String.format("Failed to write a product item { %s }: %s",
                item.toString(),
                t.getMessage()));
    }

    @Override
    public void onSkipInProcess(ProductIn item, Throwable t) {
        log.warn(String.format("Failed to read a product item { %s }: %s. Check if the record is malformed.",
                item.toString(),
                t.getMessage()));
    }

}
