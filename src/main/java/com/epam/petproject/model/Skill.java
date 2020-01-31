package com.epam.petproject.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Skill {
    private Long skillID;
    private String name;

    public Skill(Long skillID, String name) {
        this.skillID = skillID;
        this.name = name;
    }

    @Override
    public String toString() {
        return "{skillID=" + skillID +
                ", name='" + name + '\'' + "}" + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill)) return false;
        Skill skill = (Skill) o;
        return getName().equals(skill.getName()) &&
                skillID.equals(skill.skillID);
    }
    @Override
    public int hashCode() {
        return Objects.hash(skillID,name);
    }
}
