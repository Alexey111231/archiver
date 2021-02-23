package ru.vk.sladkiipirojok.archiver;

public class Util {
    private Util() {
    }

    public static void errorPropertyAssert(String propertyName, String propertyValue) {
        if (propertyValue == null) {
            throw new IllegalStateException("Not found property: " + propertyName);
        }
    }
}
