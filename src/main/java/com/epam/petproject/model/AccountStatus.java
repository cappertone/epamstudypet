package com.epam.petproject.model;

public enum AccountStatus {


    ACTIVE {
        public String parseType(AccountStatus accountStatus) {
            return String.valueOf(ACTIVE);
        }
    },
    BANNED {
        public String parseType(AccountStatus accountStatus) {
            return String.valueOf(BANNED
            );
        }
    },
    INACTIVE {
        public String parseType(AccountStatus accountStatus) {
            return String.valueOf(INACTIVE);
        }
    };

    public abstract String parseType(AccountStatus accountStatus);
//
//
//    public static Options parseType(String value) {
//        if (value.equalsIgnoreCase(String.valueOf(ROCK))) {
//            return ROCK;
//        } else if (value.equalsIgnoreCase(String.valueOf(PAPER))) {
//            return PAPER;
//        } else if (value.equalsIgnoreCase(String.valueOf(SCISSORS))) {
//            return SCISSORS;
//        }
//        return null;
//    }
}
