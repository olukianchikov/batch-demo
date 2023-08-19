package com.lukianchikov.lego.batch.util;

import com.lukianchikov.lego.processor.batch.util.DataTypeUtil;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DataTypeUtilTest {
    @Test
    void given_big_decimal_with_precision_2_converts() {
        assertThat(DataTypeUtil.parsePrice("100.02")).hasScaleOf(2);
        assertThat(DataTypeUtil.parsePrice("100.02")).isCloseTo(new BigDecimal("100.02"), Percentage.withPercentage(0.1));
    }

    @Test
    void given_negative_big_decimal_with_precision_3_converts() {
        assertThat(DataTypeUtil.parsePrice("-15.025")).hasScaleOf(3);
        assertThat(DataTypeUtil.parsePrice("-15.025")).isCloseTo(new BigDecimal("-15.025"), Percentage.withPercentage(0.1));
    }

    @Test
    void given_big_decimal_with_precision_3_and_all_zeros_converts() {
        assertThat(DataTypeUtil.parsePrice("13.000")).hasScaleOf(3);
        assertThat(DataTypeUtil.parsePrice("13.000")).isCloseTo(new BigDecimal("13"), Percentage.withPercentage(0.1));
    }


    @Test
    void given_date_string_format_converts_to_long() {
        String format = "yyyy-MM-dd HH:mm:ss";
        Long res = DataTypeUtil.getLongFromStringDateTime("2008-10-31 01:30:00",
                new String[]{format}, "GMT");
        assertThat(res).isEqualTo(1225416600L);
    }
}
