package ru.mirea.lilkhalil.instruction.resolver.impl;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.mirea.lilkhalil.instruction.resolver.InstructionResolver;

@Component
@Getter
public class HltInstructionResolver implements InstructionResolver {

    private final String name;

    public HltInstructionResolver() {
        this.name = "HLT";
    }

    @Override
    public int resolve(String instruction) {
        return 0;
    }
}
