package com.lukianchikov.lego.processor.batch.converter;

import com.lukianchikov.lego.processor.batch.exception.ItemInvalidException;
import com.lukianchikov.lego.processor.batch.model.Product;
import com.lukianchikov.lego.processor.batch.model.ProductIn;
import com.lukianchikov.lego.processor.batch.util.DataTypeUtil;

public class ProductConverter {

    private final String[] formats;
    private final String timezoneName;

    public ProductConverter(String[] formats, String timezoneName) {
        this.formats = formats;
        this.timezoneName = timezoneName;
    }

    /**
     * Convert product received from reading the input data record into a version
     * that should be stored in the output. All necessary data format conversions happen here.
     */
    public Product convert(ProductIn input) {
        try {
            return Product.builder()
                    .id(input.getId())
                    .name(input.getName())
                    .stock(DataTypeUtil.getIntFromString(input.getStock()))
                    .created(DataTypeUtil.getLongFromStringDateTime(input.getCreated(), this.formats, this.timezoneName))
                    .updated(DataTypeUtil.getLongFromStringDateTime(input.getUpdated(), this.formats, this.timezoneName))
                    .build();
        } catch (Exception exception) {
            throw new ItemInvalidException(String.format("Failed to convert input product record { %s }", input), exception);
        }
    }
}
