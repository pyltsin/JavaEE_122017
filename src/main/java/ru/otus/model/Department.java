package ru.otus.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Department extends AbstractBaseEntity {
    private String name;
}
