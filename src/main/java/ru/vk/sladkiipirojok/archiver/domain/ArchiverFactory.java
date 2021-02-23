package ru.vk.sladkiipirojok.archiver.domain;

import ru.vk.sladkiipirojok.archiver.Util;

import java.lang.reflect.InvocationTargetException;

public class ArchiverFactory {
    private static final ArchiverFactory instance;
    private static final String ARCHIVER_TYPE_PROPERTY = "archiver.class";
    static {
        instance = new ArchiverFactory();
    }

    private ArchiverFactory() {
    }

    public Archiver createArchiver() {
        String archiverClass = System.getProperties().getProperty(ARCHIVER_TYPE_PROPERTY);
        Util.errorPropertyAssert(ARCHIVER_TYPE_PROPERTY, archiverClass);
        try {
            return (Archiver) Class.forName(archiverClass).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Error in property " + ARCHIVER_TYPE_PROPERTY + ": " + archiverClass);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Not found constructor without parameters: " + archiverClass);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArchiverFactory instance() {
        return instance;
    }
}
