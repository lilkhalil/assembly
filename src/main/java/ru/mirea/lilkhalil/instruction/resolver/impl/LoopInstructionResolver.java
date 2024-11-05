package ru.mirea.lilkhalil.instruction.resolver.impl;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.mirea.lilkhalil.instruction.resolver.InstructionResolver;
import ru.mirea.lilkhalil.registry.LabelRegistry;

@Component
public class LoopInstructionResolver implements InstructionResolver {

    @Getter
    private final String name;
    private final LabelRegistry labelRegistry;

    public LoopInstructionResolver(LabelRegistry labelRegistry) {
        this.name = "LOOP";
        this.labelRegistry = labelRegistry;
    }

    @Override
    public int resolve(String instruction) {
        String[] parts = instruction.split("\\s+");

        String label = parts[1].toLowerCase();

        int indirect = labelRegistry.get(label);

        return (0x8 & 0xF) << 28 | (indirect & 0xFFFFF) << 8;
    }
}
