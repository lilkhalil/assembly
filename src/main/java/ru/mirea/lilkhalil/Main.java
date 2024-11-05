package ru.mirea.lilkhalil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.mirea.lilkhalil.assembler.Assembler;
import ru.mirea.lilkhalil.configuration.AssemblerConfiguration;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static ru.mirea.lilkhalil.utils.Utils.DESTINATION_FILE;
import static ru.mirea.lilkhalil.utils.Utils.SOURCE_FILE;

@Slf4j
public class Main
{

    public static void main(String ... args) {
        String sourceFile = parseSourceFile();

        ApplicationContext context = new AnnotationConfigApplicationContext(AssemblerConfiguration.class);

        Assembler assembler = context.getBean(Assembler.class);

        String result = assembler.assemble(sourceFile);

        writeToFile(result);
    }

    private static String parseSourceFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SOURCE_FILE))) {
            return reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty() && !line.startsWith(";"))
                    .collect(Collectors.joining("\n"));
        } catch (IOException ex) {
            log.error("Ошибка загрузки файла исходного кода программы", ex);
        }
        return null;
    }

    private static void writeToFile(String data) {
        try (FileOutputStream outputStream = new FileOutputStream(DESTINATION_FILE)) {
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            outputStream.write(bytes);
        } catch (IOException ex) {
            log.error("Ошибка записи файла машинного кода программы", ex);
        }
    }
}
