package com.lukianchikov.lego.batch.converter;

import com.lukianchikov.lego.processor.batch.converter.ProductConverter;
import com.lukianchikov.lego.processor.batch.exception.ItemInvalidException;
import com.lukianchikov.lego.processor.batch.model.Product;
import com.lukianchikov.lego.processor.batch.model.ProductIn;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductConverterTest {

    @Test
    void given_valid_input_converts() {
        ProductIn input = ProductIn.builder()
                .id("sss-810-qqq")
                .name("Dungeons & Dragons")
                .stock("111")
                .created("2015-02-22 10:22:13 PM")
                .updated("2018-12-05 07:10:45 PM")
                .build();
        ProductConverter productConverter = new ProductConverter(new String[]{"yyyy-MM-dd hh:mm:ss a"},
                "GMT");
        Product res = productConverter.convert(input);
        assertThat(res.getId()).isEqualTo("sss-810-qqq");
        assertThat(res.getName()).isEqualTo("Dungeons & Dragons");
        assertThat(res.getStock()).isEqualTo(111);
        assertThat(res.getCreated()).isEqualTo(1424643733L);
        assertThat(res.getUpdated()).isEqualTo(1544037045L);
    }


    @Test
    void given_invalid_stock_then_fails() {
        ProductIn input = ProductIn.builder()
                .id("sss-810-qqq")
                .name("Dungeons & Dragons")
                .stock("11-1")
                .created("2015-02-22 10:22:13 PM")
                .updated("2018-12-05 07:10:45 PM")
                .build();
        ProductConverter productConverter = new ProductConverter(new String[]{"yyyy-MM-dd hh:mm:ss a"},
                "GMT");
        assertThatThrownBy(() -> productConverter.convert(input)).isInstanceOf(ItemInvalidException.class);
    }


    @Test
    void given_invalid_date_format_then_fails() {
        ProductIn input = ProductIn.builder()
                .id("sss-810-qqq")
                .name("Dungeons & Dragons")
                .stock("11")
                .created("2015-02-22 10:22:13 PM")
                .updated("2018-12-05 07:10:45 PM")
                .build();
        ProductConverter productConverter = new ProductConverter(new String[]{"yyyy/MM/dd hh:mm:ss a"},
                "GMT");
        assertThatThrownBy(() -> productConverter.convert(input)).isInstanceOf(ItemInvalidException.class);
    }
}
