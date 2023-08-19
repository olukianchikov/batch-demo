package com.lukianchikov.lego.processor.batch.writable;

import com.lukianchikov.lego.processor.batch.model.Price;
import com.lukianchikov.lego.processor.batch.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class WritableResourceProviderConfig {
    private final ResourceLoader resourceLoader;
    private final ProductTargetPathMaker productTargetPathMaker;
    private final PriceTargetPathMaker priceTargetPathMaker;


    @Autowired
    public WritableResourceProviderConfig(ResourceLoader resourceLoader,
                                          ProductTargetPathMaker productTargetPathMaker,
                                          PriceTargetPathMaker priceTargetPathMaker) {
        this.resourceLoader = resourceLoader;
        this.productTargetPathMaker = productTargetPathMaker;
        this.priceTargetPathMaker = priceTargetPathMaker;
    }

    @Bean("WritableResourceProviderProduct")
    public WritableResourceProvider<Product> getWritableResourceProviderProduct() {
        return new WritableResourceProvider<>(resourceLoader, productTargetPathMaker);
    }

    @Bean("WritableResourceProviderPrice")
    public WritableResourceProvider<Price> getWritableResourceProviderPrice() {
        return new WritableResourceProvider<>(resourceLoader, priceTargetPathMaker);
    }
}
