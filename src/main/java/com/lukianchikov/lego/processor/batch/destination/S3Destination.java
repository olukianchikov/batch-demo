package com.lukianchikov.lego.processor.batch.destination;

import com.lukianchikov.lego.processor.config.AppProperties;

public class S3Destination extends Destination {

    private final AppProperties appProperties;

    public S3Destination(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public String getTargetContainerPath(String containerName) {
        return String.format("s3://%s/%s", appProperties.getS3Bucket(), containerName);
    }

    @Override
    public boolean isLocalFileSystem() {
        return false;
    }
}
