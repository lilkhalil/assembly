package ru.mirea.lilkhalil.instruction.impl;

import org.springframework.stereotype.Component;
import ru.mirea.lilkhalil.instruction.resolver.InstructionResolver;
import ru.mirea.lilkhalil.instruction.InstructionSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InstructionSetImpl implements InstructionSet {

    private final Map<String, InstructionResolver> resolvers = new HashMap<>();

    public InstructionSetImpl(List<InstructionResolver> resolvers) {
        for (InstructionResolver resolver : resolvers)
            this.resolvers.put(resolver.getName(), resolver);
    }

    @Override
    public InstructionResolver getResolver(String instructionName) {
        return resolvers.computeIfAbsent(instructionName, name -> {
            throw new IllegalArgumentException("Инструкция с именем=%s не поддерживается".formatted(name));
        });
    }
}
