package ru.vk.sladkiipirojok.archiver.input;

import ru.vk.sladkiipirojok.archiver.Util;

import java.lang.reflect.InvocationTargetException;

public class InputFactory {
    private static final InputFactory instance;
    private static final String INPUT_TYPE_PROPERTY = "input.class";
    static {
        instance = new InputFactory();
    }

    private InputFactory() {
    }

    public Input createInput() {
        String inputClass = System.getProperties().getProperty(INPUT_TYPE_PROPERTY);
        Util.errorPropertyAssert(INPUT_TYPE_PROPERTY, inputClass);
        try {
            return (Input) Class.forName(inputClass).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Error in property " + INPUT_TYPE_PROPERTY + ": " + inputClass);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Not found constructor without parameters: " + inputClass);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputFactory instance() {
        return instance;
    }
}
