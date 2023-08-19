package com.lukianchikov.lego.processor.batch.destination;

import com.lukianchikov.lego.processor.config.AppProperties;

/**
 * Represent a destination for processor, i.e. local filesystem.
 */
public abstract class Destination {

    public static Destination of(AppProperties appProperties) {
        return switch (appProperties.getDestination()) {
            case "s3" -> new S3Destination(appProperties);
            case "fs" -> new FSDestination(appProperties);
            default -> throw new IllegalArgumentException(
                    String.format("Unrecognized destination %s", appProperties.getDestination()));
        };
    }

    /**
     * Get the path of the target "container". It can be an absolute path to a folder on local filesystem.
     */
    public abstract String getTargetContainerPath(String containerName);

    public abstract boolean isLocalFileSystem();
}
