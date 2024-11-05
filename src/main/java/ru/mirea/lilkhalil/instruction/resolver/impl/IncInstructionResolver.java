package ru.mirea.lilkhalil.instruction.resolver.impl;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.mirea.lilkhalil.instruction.resolver.InstructionResolver;
import ru.mirea.lilkhalil.registry.RegisterSet;

@Component
public class IncInstructionResolver implements InstructionResolver {

    @Getter
    private final String name;
    private final RegisterSet registerSet;

    public IncInstructionResolver(RegisterSet registerSet) {
        this.name = "INC";
        this.registerSet = registerSet;
    }

    @Override
    public int resolve(String instruction) {
        String[] parts = instruction.split("\\s+");

        int registerIdx = registerSet.get(parts[1].toLowerCase());

        return (0x7 & 0xF) << 28 | (0) << 8 | (registerIdx & 0xF) << 4;
    }
}
