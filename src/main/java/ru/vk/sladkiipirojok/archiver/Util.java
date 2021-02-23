package ru.vk.sladkiipirojok.archiver;

public class Util {
    private Util() {
    }

    /**
     * Метод должен быть использован для проверки получения системного свойства
     *
     * @param propertyName  Ключ свойства
     * @param propertyValue Значение свойства
     */
    public static void errorPropertyAssert(String propertyName, String propertyValue) {
        if (propertyValue == null) {
            throw new IllegalStateException("Not found property: " + propertyName);
        }
    }
}
