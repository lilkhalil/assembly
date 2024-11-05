package ru.mirea.lilkhalil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.mirea.lilkhalil.assembler.Assembler;
import ru.mirea.lilkhalil.configuration.AssemblerConfiguration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

import static ru.mirea.lilkhalil.utils.Utils.SOURCE_FILE;

@Slf4j
public class Main
{

    public static void main(String ... args) {
        String sourceFile = parseSourceFile();

        ApplicationContext context = new AnnotationConfigApplicationContext(AssemblerConfiguration.class);

        Assembler assembler = context.getBean(Assembler.class);

        String result = assembler.assemble(sourceFile);

        System.out.println(result);
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
}
