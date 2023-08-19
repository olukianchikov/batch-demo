package com.lukianchikov.lego.processor.batch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private String id;
    private String name;
    private BigDecimal price;
    private String currency;
}
