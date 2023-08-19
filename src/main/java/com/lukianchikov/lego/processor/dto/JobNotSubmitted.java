package com.lukianchikov.lego.processor.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class JobNotSubmitted extends ProcessorResponse {
    String message;
}
