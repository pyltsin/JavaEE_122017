package ru.otus.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class City extends AbstractBaseEntity {
    private String name;
}
