package com.lukianchikov.lego.processor.batch.product;

import com.lukianchikov.lego.processor.batch.converter.ProductConverter;
import com.lukianchikov.lego.processor.batch.model.Product;
import com.lukianchikov.lego.processor.batch.model.ProductIn;
import com.lukianchikov.lego.processor.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductItemProcessor implements ItemProcessor<ProductIn, Product> {

    private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);

    private final AppProperties appProperties;
    private final ProductConverter converter;

    @Autowired
    public ProductItemProcessor(AppProperties appProperties) {
        this.appProperties = appProperties;
        this.converter = new ProductConverter(this.appProperties.getInputDateFormats(), this.appProperties.getTimezone());
    }

    @Override
    public Product process(final ProductIn productIn) {
        final Product transformedProduct = converter.convert(productIn);
        log.info("Converting product (" + productIn + ") into product (" + transformedProduct + ")");
        return transformedProduct;
    }
}
