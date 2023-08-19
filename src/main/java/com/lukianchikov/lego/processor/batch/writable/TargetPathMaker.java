package com.lukianchikov.lego.processor.batch.writable;

import com.lukianchikov.lego.processor.batch.destination.Destination;

/**
 * Maker of a path where output of processing will be saved. Depending on the system, it can be a
 * local filesystem path, or some remote system path/url.
 *
 * @param <T>
 */
public interface TargetPathMaker<T> {
    String getTargetPath();

    Destination getTargetDestination();

    String getTargetContainer();
}
