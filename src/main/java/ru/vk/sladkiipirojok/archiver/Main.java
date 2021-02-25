package ru.vk.sladkiipirojok.archiver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    private static final String APPLICATION_PROPERTIES = "archiver.properties";
    private static final String FILES_KEY_PROPERTY = "input.property.key";
    private static final String SEPARATOR_KEY = "files.string.separator";

    public static void main(String[] args) {
        try {
            InputStream applicationProperties = Main.class.getResourceAsStream(APPLICATION_PROPERTIES);
            System.getProperties().load(applicationProperties);
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("Error in property file: \"archiver.properties\"", e);
        }

        //В данной реализации main, данные передаются в системные проперти
        //Затем мы их получем из класс PropertyInput и выполняем все необходимые действия
        StringBuilder files = new StringBuilder();
        String separator = getSystemProperty().getProperty(SEPARATOR_KEY);
        Util.errorPropertyAssert(SEPARATOR_KEY, separator);
        Consumer<String> append = s -> files.append(s).append(separator);

        boolean isArchive = false;
        //Читаем либо аргументы, либо данные из pipe
        if (args.length != 0) {
            Arrays.stream(args).forEach(append);
            isArchive = true;
        } else if (!systemInEmpty()) {//Защита от зависания при пустом потоке
            //Скорее всего надо заменить сканнер обычным Reader
            Scanner scanner = new Scanner(System.in);

            if (scanner.hasNext()) {
                scanner.forEachRemaining(append);
            }
        }

        if (assertEmptyInput(files.toString(), separator)) {
            System.out.println("Empty input");
            return;
        }

        String filesProperty = getSystemProperty().getProperty(FILES_KEY_PROPERTY);
        Util.errorPropertyAssert(FILES_KEY_PROPERTY, filesProperty);

        getSystemProperty().setProperty(filesProperty, files.toString());

        ArchiverApplication.oneWay(isArchive);
    }

    private static boolean assertEmptyInput(String inputString, String separator) {
        return inputString.trim().replaceAll(separator, "").isEmpty();
    }

    private static Properties getSystemProperty() {
        return System.getProperties();
    }

    private static boolean systemInEmpty() {
        boolean emptyInput;
        try {
            emptyInput = new BufferedInputStream(System.in).available() == 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return emptyInput;
    }
}

