package com.epam.petproject.model;

import java.util.Set;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
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
