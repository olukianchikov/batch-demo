package com.lukianchikov.lego.processor.batch.price;

import com.lukianchikov.lego.processor.batch.converter.PriceConverter;
import com.lukianchikov.lego.processor.batch.model.Price;
import com.lukianchikov.lego.processor.batch.model.PriceIn;
import com.lukianchikov.lego.processor.batch.product.ProductItemProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceItemProcessor implements ItemProcessor<PriceIn, Price> {

    private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);

    private final PriceConverter converter;

    @Autowired
    public PriceItemProcessor() {
        this.converter = new PriceConverter();
    }

    @Override
    public Price process(final PriceIn priceIn) throws Exception {
        final Price price = converter.convert(priceIn);
        log.info("Converting price (" + priceIn + ") into price (" + price + ")");
        return price;
    }
}
