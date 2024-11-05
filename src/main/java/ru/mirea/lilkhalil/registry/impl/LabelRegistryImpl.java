package ru.mirea.lilkhalil.registry.impl;

import org.springframework.stereotype.Component;
import ru.mirea.lilkhalil.registry.LabelRegistry;

import java.util.HashMap;
import java.util.Map;

@Component
public class LabelRegistryImpl implements LabelRegistry {

    private final Map<String, Integer> labels = new HashMap<>();

    @Override
    public void register(String label, Integer value) {
        labels.put(label, value);
    }

    @Override
    public Integer get(String label) {
        return labels.computeIfAbsent(label, l -> {
            throw new IllegalArgumentException("Данная метка %s не найдена".formatted(l));
        });
    }
}
