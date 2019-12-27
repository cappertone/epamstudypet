package com.epam.petproject.model;

import java.util.Objects;

public class Skill {
    private Long skillID;
    private String name;


    public Skill(Long skillID, String name) {
        this.skillID = skillID;
        this.name = name;
    }

    private String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkillName() {
        return name;
    }

    public Long getSkillId() {
        return skillID;
    }

    public void setSkillId(Long skillId) {
        this.skillID = skillId;
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
        return Objects.hash(getName(), skillID);
    }
}
