package ru.mirea.lilkhalil.instruction.resolver.impl;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.mirea.lilkhalil.instruction.resolver.InstructionResolver;
import ru.mirea.lilkhalil.registry.RegisterSet;

import java.util.Arrays;

@Component
public class MovInstructionResolver implements InstructionResolver {

    @Getter
    private final String name;
    private final RegisterSet registerSet;

    public MovInstructionResolver(RegisterSet registerSet) {
        this.name = "MOV";
        this.registerSet = registerSet;
    }

    @Override
    public int resolve(String instruction) {
        String[] parts = instruction.split("\\s+");

        String[] operands = Arrays.stream(parts[1].split(","))
                .map(String::toLowerCase)
                .toArray(String[]::new);

        int registerIdx = registerSet.get(operands[0]);
        int sourceIdx = registerSet.get(operands[1]);

        return (0x2 & 0xF) << 28 | (0) << 8 | (registerIdx & 0xF) << 4 | (sourceIdx & 0xF);
    }
}
