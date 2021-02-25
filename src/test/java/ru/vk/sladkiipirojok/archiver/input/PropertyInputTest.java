package ru.vk.sladkiipirojok.archiver.input;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

class PropertyInputTest {
    private static final String FILES_KEY_PROPERTY = "input.property.key";
    private static final String SEPARATOR_KEY = "files.string.separator";

    private static Properties systemProperties;

    @BeforeAll
    static void setUp() {
        systemProperties = (Properties) System.getProperties().clone();
    }

    @AfterEach
    void restoreSystemProperty() {
        System.setProperties(systemProperties);
    }

    @Test
    void Read_NoPropertyForPropertyKey_ThrownIllegalStateException() {
        propertyLoad("no-files-key.properties");
        PropertyInput propertyInput = new PropertyInput();
        Assertions.assertThrows(IllegalStateException.class, propertyInput::read);
    }

    @Test
    void Read_NoPropertyForSeparator_ThrownIllegalStateException() {
        propertyLoad("no-separator.properties");
        PropertyInput propertyInput = new PropertyInput();
        Assertions.assertThrows(IllegalStateException.class, propertyInput::read);
    }

    @Test
    void Read_CorrectPropertiesAndNoSetFilesProperty_ThrownIllegalStateException() {
        propertyLoad("correct.properties");
        PropertyInput propertyInput = new PropertyInput();
        Assertions.assertThrows(IllegalStateException.class, propertyInput::read);
    }

    @Test
    void Read_CorrectPropertiesAndSetFilesProperty_ReturnListFilesCorrectLength() {
        propertyLoad("correct.properties");

        String filesKey = System.getProperties().getProperty(FILES_KEY_PROPERTY);
        String separator = System.getProperties().getProperty(SEPARATOR_KEY);

        System.getProperties().setProperty(filesKey, "test" + separator + "test2" + separator + "test3");
        PropertyInput propertyInput = new PropertyInput();
        Assertions.assertEquals(3, propertyInput.read().size());
    }

    @Test
    void Read_CorrectPropertiesAndSetFilesPropertyEmpty_ReturnEmptyList() {
        propertyLoad("correct.properties");

        String filesKey = System.getProperties().getProperty(FILES_KEY_PROPERTY);
        String separator = System.getProperties().getProperty(SEPARATOR_KEY);

        System.getProperties().setProperty(filesKey, "");
        PropertyInput propertyInput = new PropertyInput();
        Assertions.assertEquals(0, propertyInput.read().size());
    }

    /**
     * Каждый тест должен устновит свои property
     *
     * @param propertyPath путь к файлу конфигурации
     */
    private static void propertyLoad(String propertyPath) {
        try {
            Properties properties = new Properties();
            properties.load(PropertyInputTest.class.getResourceAsStream(propertyPath));
            System.setProperties(properties);
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("Error in property file: \"archiver.properties\"");
        }
    }
}