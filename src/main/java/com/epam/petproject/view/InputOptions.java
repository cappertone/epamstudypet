package com.epam.petproject.view;

public enum InputOptions {
    GETALL,
    SAVE,
    READ,
    UPDATE,
    DELETE;

    public static InputOptions parseType(String value) {
        if (value.equalsIgnoreCase(String.valueOf(GETALL))) {
            return GETALL;
        }else if (value.equalsIgnoreCase(String.valueOf(SAVE))) {
            return SAVE;
        } else if (value.equalsIgnoreCase(String.valueOf(READ))) {
            return READ;
        } else if (value.equalsIgnoreCase(String.valueOf(UPDATE))) {
            return UPDATE;
        } else if (value.equalsIgnoreCase(String.valueOf(DELETE))) {
            return DELETE;
        }
        return null;
    }
}
