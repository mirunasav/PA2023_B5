package org.example.compulsory;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class Utilities {
    public static boolean isPathValid(String path) {
        try {

            Paths.get(path);

        } catch (InvalidPathException ex) {
            return false;
        }

        return true;
    }
}
