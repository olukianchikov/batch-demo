package com.lukianchikov.lego.processor.batch.converter;

import com.lukianchikov.lego.processor.batch.exception.ItemInvalidException;
import com.lukianchikov.lego.processor.batch.model.Price;
import com.lukianchikov.lego.processor.batch.model.PriceIn;
import com.lukianchikov.lego.processor.batch.util.DataTypeUtil;

public class PriceConverter {

    /**
     * Convert price received from reading the input data record into a version
     * that should be stored in the output. Examples: data format conversions.
     */
    public Price convert(PriceIn input) {
        try {
            return Price.builder()
                    .id(input.getId())
                    .name(input.getName())
                    .price(DataTypeUtil.parsePrice(input.getPrice()))
                    .currency(input.getCurrency().toUpperCase())
                    .build();
        } catch (Exception exception) {
            throw new ItemInvalidException(String.format("Failed to convert input price record { %s }", input), exception);
        }
    }
}
