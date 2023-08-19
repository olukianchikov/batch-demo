package com.lukianchikov.lego.processor.batch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceIn {
    private String id;
    private String name;
    private String price;
    private String currency;
}
