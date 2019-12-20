package com.epam.petproject.view;

public enum InputOptions {

    CREATE,
    READ,
    UPDATE,
    DELETE;

    public static InputOptions parseType(String value) {
        if (value.equalsIgnoreCase(String.valueOf(CREATE))) {
            return CREATE;
        } else if (value.equalsIgnoreCase(String.valueOf(READ))) {
            return READ;
        } else if (value.equalsIgnoreCase(String.valueOf(UPDATE))) {
            return UPDATE;
        }else if (value.equalsIgnoreCase(String.valueOf(DELETE))) {
            return DELETE;
        }
        return null;
    }
}
