package com.lukianchikov.lego.processor.batch.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductIn {
    private String id;
    private String name;
    private String stock;
    private String created;
    private String updated;
}
