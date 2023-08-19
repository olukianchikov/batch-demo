package com.lukianchikov.lego.processor.batch.writable;

import com.lukianchikov.lego.processor.batch.destination.Destination;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;

import java.util.logging.Logger;

public class WritableResourceProvider<T> {

    Logger logger = Logger.getLogger(WritableResourceProvider.class.getName());

    private final ResourceLoader resourceLoader;
    private final TargetPathMaker<T> targetPath;

    public WritableResourceProvider(ResourceLoader resourceLoader, TargetPathMaker<T> targetPath) {
        this.resourceLoader = resourceLoader;
        this.targetPath = targetPath;
    }

    public WritableResource getWritableResource() {
        Destination destination = this.targetPath.getTargetDestination();
        if (destination.isLocalFileSystem()) {
            return new FileSystemResource(this.targetPath.getTargetPath());
        } else {
            return (WritableResource) resourceLoader
                    .getResource(this.targetPath.getTargetPath());
        }

    }
}
