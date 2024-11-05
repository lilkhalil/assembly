package ru.mirea.lilkhalil.assembler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mirea.lilkhalil.assembler.Assembler;
import ru.mirea.lilkhalil.processor.Processor;
import ru.mirea.lilkhalil.registry.LabelRegistry;
import ru.mirea.lilkhalil.registry.ProcessorRegistry;

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

        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            if (line.startsWith("section")) {
                sectionName = line.split("\\s+")[1];
            } else {
                Processor processor = processorRegistry.get(sectionName);
                result.append(processor.process(line)).append('\n');
            }
        }

        return result.toString();
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
