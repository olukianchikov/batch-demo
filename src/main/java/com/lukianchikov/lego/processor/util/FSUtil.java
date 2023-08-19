package com.lukianchikov.lego.processor.util;

import java.io.File;

public class FSUtil {
    private FSUtil() {
    }

    public static boolean pathExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static boolean isFile(String path) {
        File file = new File(path);
        return file.isFile();
    }
}
