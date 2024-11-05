package ru.mirea.lilkhalil.instruction.resolver.impl;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.mirea.lilkhalil.instruction.resolver.InstructionResolver;
import ru.mirea.lilkhalil.registry.LabelRegistry;
import ru.mirea.lilkhalil.registry.RegisterSet;

import java.util.Arrays;

@Component
public class LoadInstructionResolver implements InstructionResolver {

    @Getter
    private final String name;
    private final RegisterSet registerSet;
    private final LabelRegistry labelRegistry;

    public LoadInstructionResolver(RegisterSet registerSet, LabelRegistry labelRegistry) {
        this.name = "LD";
        this.registerSet = registerSet;
        this.labelRegistry = labelRegistry;
    }

    @Override
    public int resolve(String instruction) {
        String[] parts = instruction.split("\\s+");

        String[] operands = Arrays.stream(parts[1].split(","))
                .map(String::toLowerCase)
                .toArray(String[]::new);

        int registerIdx = registerSet.get(operands[0]);
        int sourceIdx = labelRegistry.get(operands[1].replaceAll("[\\[\\]]", ""));

        return (0x3 & 0xF) << 28 | (0) << 8 | (registerIdx & 0xF) << 4 | (sourceIdx & 0xF);
    }

}
