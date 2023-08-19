package com.lukianchikov.lego.processor.batch.util;

import java.io.File;
import java.util.Objects;

public class FSUtils {

    private FSUtils() {
    }

    public static boolean makeDirs(String path) {
        if (Objects.isNull(path)) {
            return false;
        }
        File pathToCreate = new File(path);
        if (pathToCreate.exists() && pathToCreate.isDirectory()) {
            return true;
        } else if (pathToCreate.exists() && pathToCreate.isFile()) {
            return false;
        } else {
            return pathToCreate.mkdirs();
        }
    }
}
