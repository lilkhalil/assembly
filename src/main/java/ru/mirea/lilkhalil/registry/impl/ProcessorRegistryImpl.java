package ru.mirea.lilkhalil.registry.impl;

import org.springframework.stereotype.Component;
import ru.mirea.lilkhalil.processor.Processor;
import ru.mirea.lilkhalil.registry.ProcessorRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProcessorRegistryImpl implements ProcessorRegistry {

    private final Map<String, Processor> processors = new HashMap<>();

    public ProcessorRegistryImpl(List<Processor> processors) {
        for (Processor processor : processors)
            this.processors.put(processor.getName(), processor);
    }

    @Override
    public Processor get(String sectionName) {
        return processors.computeIfAbsent(sectionName, name -> {
            throw new IllegalArgumentException("Процессора для данной секции не существует!");
        });
    }
}
