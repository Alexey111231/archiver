package ru.vk.sladkiipirojok.archiver.output;

import ru.vk.sladkiipirojok.archiver.Util;

import java.lang.reflect.InvocationTargetException;

/**
 * Фабрика выходов,
 * Берет данные о необходимом классе из системных property
 */
public class OutputFactory {
    private static final OutputFactory instance;
    private static final String OUTPUT_TYPE_PROPERTY = "output.class";
    static {
        instance = new OutputFactory();
    }

    private OutputFactory() {
    }

    public Output createOutput() {
        String outputClass = System.getProperties().getProperty(OUTPUT_TYPE_PROPERTY);
        Util.errorPropertyAssert(OUTPUT_TYPE_PROPERTY, outputClass);
        try {
            return (Output) Class.forName(outputClass).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Error in property " + OUTPUT_TYPE_PROPERTY + ": " + outputClass, e);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Not found constructor without parameters: " + outputClass, e);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static OutputFactory instance() {
        return instance;
    }
}
