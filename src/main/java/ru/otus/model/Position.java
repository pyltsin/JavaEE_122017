package ru.otus.model;

import java.util.stream.Stream;

public enum Position {
    MANAGER, HR, ACCOUANTER, CHIEF;

    public static Position valueOf(int positionId) {
        return Stream.of(Position.values()).filter(position -> position.ordinal() == positionId)
                .findFirst().orElse(Position.MANAGER);
    }
}
