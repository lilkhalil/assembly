package ru.mirea.lilkhalil.instruction;

import ru.mirea.lilkhalil.instruction.resolver.InstructionResolver;

/**
 * Интерфейс {@code InstructionSet} определяет метод для получения инструкции
 * на основе ее имени.
 */
public interface InstructionSet {
    /**
     * Возвращает парсер инструкции, соответствующую заданному имени.
     *
     * @param instructionName наименовании инструкции, определяющее логику разбора
     * @return объект {@link InstructionResolver}, соответствующий указанному наименованию операции
     */
    InstructionResolver getResolver(String instructionName);
}
