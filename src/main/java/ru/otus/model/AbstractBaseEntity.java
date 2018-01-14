package ru.otus.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractBaseEntity {
    private Long id;

    public boolean isNew() {
        return id == null;
    }
}
