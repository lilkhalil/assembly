package ru.mirea.lilkhalil.registry;

public interface LabelRegistry {
    void register(String label, Integer value);
    Integer get(String label);
}
