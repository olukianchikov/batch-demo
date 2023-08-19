package com.lukianchikov.lego.batch.converter;

import com.lukianchikov.lego.processor.batch.converter.PriceConverter;
import com.lukianchikov.lego.processor.batch.exception.ItemInvalidException;
import com.lukianchikov.lego.processor.batch.model.Price;
import com.lukianchikov.lego.processor.batch.model.PriceIn;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PriceConverterTest {

    @Test
    void given_valid_input_converts() {
        PriceIn input = PriceIn.builder()
                .id("xx123-yyy")
                .name("fOObar")
                .price("35.1")
                .currency("CaD")
                .build();
        PriceConverter converter = new PriceConverter();
        Price res = converter.convert(input);

        assertThat(res.getId()).isEqualTo("xx123-yyy");
        assertThat(res.getName()).isEqualTo("fOObar");
        assertThat(res.getPrice()).isCloseTo(new BigDecimal("35.1"), Percentage.withPercentage(0.01));
        assertThat(res.getCurrency()).isEqualTo("CAD");
    }

    @Test
    void given_invalid_price_fails() {
        PriceIn input = PriceIn.builder()
                .id("xx123-yyy")
                .name("foobar")
                .price("3.5.1")
                .currency("CAD")
                .build();
        PriceConverter converter = new PriceConverter();
        assertThatThrownBy(() -> converter.convert(input)).isInstanceOf(ItemInvalidException.class);
    }
}
