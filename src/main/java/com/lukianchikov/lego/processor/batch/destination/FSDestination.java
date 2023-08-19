package com.lukianchikov.lego.processor.batch.destination;

import com.lukianchikov.lego.processor.batch.util.FSUtils;
import com.lukianchikov.lego.processor.config.AppProperties;

public class FSDestination extends Destination {

    private final AppProperties appProperties;

    public FSDestination(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public String getTargetContainerPath(String containerName) {
        final String fsOutput = appProperties.getFsOutput();
        String path = String.format("%s/%s", fsOutput, containerName);
        if (FSUtils.makeDirs(path)) {
            return path;
        } else {
            throw new RuntimeException(String.format("Failed to create directories for destination path: %s", fsOutput));
        }
    }

    @Override
    public boolean isLocalFileSystem() {
        return true;
    }
}
