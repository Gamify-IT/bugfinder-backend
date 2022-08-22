package de.unistuttgart.bugfinder.util;

import java.util.UUID;

public class UuidUtil {

    public static UUID ofNullableFallbackNull(String str) {
        if (str == null) {
            return null;
        }
        try {
            return UUID.fromString(str);
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }
}
