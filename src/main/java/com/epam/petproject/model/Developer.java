package com.epam.petproject.model;

import java.util.Set;

public class Developer {
    Long id;
    Set<Skill> skills;
    Account account;

    public Developer(Long id, Set<Skill> skills, Account account) {
        this.id = id;
        this.skills = skills;
        this.account = account;
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
                "id=" + id +
                ", skills=" + skills +
                ", account=" + account +'}'+'\n';
    }
}
