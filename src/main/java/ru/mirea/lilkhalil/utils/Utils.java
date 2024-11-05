package ru.mirea.lilkhalil.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс {@code Utils} предназначен для загрузки конфигурационных параметров
 * из файла {@code application.properties} и предоставляет доступ к ним в виде
 * статических констант.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Utils {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Utils.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            log.error("Ошибка загрузки файла конфигурации: ", ex);
        }
    }

    /**
     * Путь к файлу исходного кода на ассемблере.
     * Значение по умолчанию: src/main/resources/source.txt.
     */
    public static final String SOURCE_FILE = properties.getProperty("source.file-name", "src/main/resources/source.txt");

    /**
     * Путь к файлу исходного кода на ассемблере.
     * Значение по умолчанию: src/main/resources/source.txt.
     */
    public static final String DESTINATION_FILE = properties.getProperty("destination.file-name", "C:/Users/aidar/output.bin");

    /**
     * Смещение данных в оперативной памяти.
     * Значение по умолчанию: 512.
     */
    public static final int DATA_OFFSET = Integer.parseInt(properties.getProperty("data.offset", "512"));
}
