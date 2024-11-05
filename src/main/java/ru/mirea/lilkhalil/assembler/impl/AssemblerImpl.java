package ru.mirea.lilkhalil.assembler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mirea.lilkhalil.assembler.Assembler;
import ru.mirea.lilkhalil.processor.Processor;
import ru.mirea.lilkhalil.registry.LabelRegistry;
import ru.mirea.lilkhalil.registry.ProcessorRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AssemblerImpl implements Assembler {

    private final ProcessorRegistry processorRegistry;
    private final LabelRegistry labelRegistry;

    @Override
    public String assemble(String sourceFile) {
        String[] lines = sourceFile.lines()
                .toArray(String[]::new);

        resolveLabels(sourceFile);

        String sectionName = null;

        List<String> result = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith("section")) {
                if (sectionName != null) {
                    result.add("\n");
                }
                sectionName = line.split("\\s+")[1];
            } else {
                Processor processor = processorRegistry.get(sectionName);
                String processedLine = processor.process(line);
                if (!processedLine.isEmpty())
                    result.add(processedLine);
            }
        }

        return result.stream()
                .map(line -> line.equals("\n") ? line : line + "\n")
                .collect(Collectors.joining()).trim();
    }

    private void resolveLabels(String sourceFile) {
        String[] lines = sourceFile.lines()
                .dropWhile(line -> !line.startsWith("section .text"))
                .skip(1L)
                .toArray(String[]::new);

        int address = 0;

        for (String line : lines) {
            if (line.endsWith(":")) {
                String label = line.substring(0, line.length() - 1);
                labelRegistry.register(label, address);
            } else {
                address++;
            }
        }
    }
}
