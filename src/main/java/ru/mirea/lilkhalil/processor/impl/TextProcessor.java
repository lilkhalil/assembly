package ru.mirea.lilkhalil.processor.impl;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.mirea.lilkhalil.instruction.InstructionSet;
import ru.mirea.lilkhalil.instruction.resolver.InstructionResolver;
import ru.mirea.lilkhalil.processor.Processor;
import ru.mirea.lilkhalil.registry.LabelRegistry;

@Component
public class TextProcessor implements Processor {

    private int address;
    private final LabelRegistry labelRegistry;
    private final InstructionSet instructionSet;
    @Getter
    private final String name;

    public TextProcessor(LabelRegistry labelRegistry, InstructionSet instructionSet) {
        this.address = 0;
        this.labelRegistry = labelRegistry;
        this.instructionSet = instructionSet;
        this.name = ".text";
    }

    @Override
    public String process(String line) {
        if (line.endsWith(":")) {
            return "";
        }

        String[] parts = line.split("\\s+", 2);
        String instructionName = parts[0].toUpperCase();

        InstructionResolver resolver = instructionSet.getResolver(instructionName);

        int command = resolver.resolve(line);

        return String.format("%s:%s", Integer.toBinaryString(address++), Integer.toBinaryString(command));
    }
}
