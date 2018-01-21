package ru.otus.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee extends AbstractBaseEntity {
    private String login;
    //TODO make hash
    private String password;
    private String name;
    //todo for jpa must be Department
    private Long departmentId;
    //todo for jpa must be position
    private Position position;
    private Long cityId;
    private String email;
    private Long salary;
}
