package com.epam.petproject.model;

import lombok.Data;

import java.util.Set;
@Data
public class Developer {
    private Long id;
    private String name;
    private Set<Skill> skills;
    private Account account;

    public Developer(Long id, String name, Set<Skill> skills, Account account) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.account = account;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id + "," +
                "name='" + name + "'," +'\n' +
                "skills=" + skills + "," + '\n' +
                "account=" + account + '\n' + '}' + '\n';
    }
}
