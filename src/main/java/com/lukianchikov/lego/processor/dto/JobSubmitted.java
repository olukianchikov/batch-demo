package com.lukianchikov.lego.processor.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class JobSubmitted extends ProcessorResponse {
    private String message;
    private String jobId;
    private String jobName;
}
