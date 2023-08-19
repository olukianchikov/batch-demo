package com.lukianchikov.lego.processor.batch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
    private Integer stock; // TODO: add validation - positive
    private Long created;  // the number of seconds from the epoch of 1970-01-01T00:00:00Z
    private Long updated;  // the number of seconds from the epoch of 1970-01-01T00:00:00Z
}
