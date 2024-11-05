package ru.mirea.lilkhalil.instruction.resolver.impl;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.mirea.lilkhalil.instruction.resolver.InstructionResolver;
import ru.mirea.lilkhalil.registry.LabelRegistry;
import ru.mirea.lilkhalil.registry.RegisterSet;

import java.util.Arrays;

import static ru.mirea.lilkhalil.utils.Utils.DATA_OFFSET;

@Component
public class MviInstructionResolver implements InstructionResolver {

    @Getter
    private final String name;
    private final RegisterSet registerSet;
    private final LabelRegistry labelRegistry;

    public MviInstructionResolver(RegisterSet registerSet, LabelRegistry labelRegistry) {
        this.name = "MVI";
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

        String label = operands[1];
        int offset = 0;

        if (label.contains("+")) {
            String[] partsWithOffset = label.split("\\+");
            label = partsWithOffset[0];
            offset = Integer.parseInt(partsWithOffset[1]);
        }

        int indirect = labelRegistry.get(label) + offset + DATA_OFFSET;

        return (0x1 & 0xF) << 28 | (indirect & 0xFFFFF) << 8 | (registerIdx & 0xF) << 4;
    }
}
