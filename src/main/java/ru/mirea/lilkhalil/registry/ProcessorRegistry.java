package ru.mirea.lilkhalil.registry;

import ru.mirea.lilkhalil.processor.Processor;

public interface ProcessorRegistry {
    Processor get(String sectionName);
}
