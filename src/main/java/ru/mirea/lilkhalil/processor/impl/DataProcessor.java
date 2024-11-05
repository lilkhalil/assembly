package ru.mirea.lilkhalil.processor.impl;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.mirea.lilkhalil.processor.Processor;
import ru.mirea.lilkhalil.registry.LabelRegistry;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class DataProcessor implements Processor {

    private int address;
    private final LabelRegistry labelRegistry;
    @Getter
    private final String name;

    public DataProcessor(LabelRegistry labelRegistry) {
        this.address = 0;
        this.labelRegistry = labelRegistry;
        this.name = ".data";
    }

    @Override
    public String process(String line) {
        String[] parts = line.split("\\s+");

        if (parts.length < 3 || !parts[1].equals("db"))
            throw new IllegalArgumentException("Неверное объявление секции данных");

        labelRegistry.register(parts[0].toLowerCase(), address);

        String[] variables = parts[2].split(",");

        return Arrays.stream(variables)
                .map(Integer::parseInt)
                .map(value -> String.format("%s:%s", Integer.toBinaryString(address++), Integer.toBinaryString(value)))
                .collect(Collectors.joining("\n"));
    }
}
