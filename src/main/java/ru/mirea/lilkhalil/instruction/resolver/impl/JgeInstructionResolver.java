package ru.mirea.lilkhalil.instruction.resolver.impl;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.mirea.lilkhalil.instruction.resolver.InstructionResolver;
import ru.mirea.lilkhalil.registry.LabelRegistry;

@Component
public class JgeInstructionResolver implements InstructionResolver {

    @Getter
    private final String name;
    private final LabelRegistry labelRegistry;

    public JgeInstructionResolver(LabelRegistry labelRegistry) {
        this.name = "JGE";
        this.labelRegistry = labelRegistry;
    }

    @Override
    public int resolve(String instruction) {
        String[] parts = instruction.split("\\s+");

        String label = parts[1].toLowerCase();

        int indirect = labelRegistry.get(label);

        return (0x6 & 0xF) << 28 | (indirect & 0xFFFFF) << 8;
    }
}
