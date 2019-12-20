package com.epam.petproject.model;

import java.util.Objects;

public class Skill {
    private String name;

    public Skill(String name) {
        this.name = name;
    }

    private String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill)) return false;
        Skill skill = (Skill) o;
        return getName().equals(skill.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
