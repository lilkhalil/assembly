package ru.mirea.lilkhalil.registry.impl;

import org.springframework.stereotype.Component;
import ru.mirea.lilkhalil.registry.RegisterSet;

import java.util.HashMap;
import java.util.Map;

@Component
public class RegisterSetImpl implements RegisterSet {

    private static final Map<String, Integer> registers = new HashMap<>();

    static {
        registers.put("eax", 0);
        registers.put("ebx", 1);
        registers.put("ecx", 2);
        registers.put("edx", 3);
        registers.put("esi", 4);
        registers.put("edi", 5);
        registers.put("esp", 6);
        registers.put("ebp", 7);
    }

    @Override
    public Integer get(String registerName) {
        return registers.computeIfAbsent(registerName, name -> {
            throw new IllegalArgumentException("Не существует регистра с именем=%s".formatted(name));
        });
    }
}
