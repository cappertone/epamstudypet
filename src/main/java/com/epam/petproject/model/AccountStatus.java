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
}
