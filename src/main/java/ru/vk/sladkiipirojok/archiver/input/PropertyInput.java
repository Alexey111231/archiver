package ru.vk.sladkiipirojok.archiver.input;

import ru.vk.sladkiipirojok.archiver.Util;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyInput extends Input {
    private static final String PROPERTY_KEY = "input.property.key";
    private static final String SEPARATOR_KEY = "files.string.separator";

    PropertyInput() {
    }

    /**
     * Функция читает заранее сохраненную строку с именами файлов из системных property
     * Преобразует их в список файлов и отдает дальше
     *
     * @return список входных файлов
     */
    @Override
    public List<File> read() {
        String filesProperty = System.getProperties().getProperty(PROPERTY_KEY);
        Util.errorPropertyAssert(PROPERTY_KEY, filesProperty);

        String files = System.getProperties().getProperty(filesProperty);
        Util.errorPropertyAssert(filesProperty, files);

        String separator = System.getProperties().getProperty(SEPARATOR_KEY);
        Util.errorPropertyAssert(SEPARATOR_KEY, separator);
        String[] filenames = files.trim().split(separator);
        return Arrays.stream(filenames).filter(s -> !s.isEmpty()).map(File::new).collect(Collectors.toList());
    }
}
