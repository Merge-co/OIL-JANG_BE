package com.mergeco.oiljang.common;


public enum UserRole {
    USER("USER"),
    ROLE_USER("ROLE_USER"),
    ADMIN("ADMIN"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ALL("USER,ADMIN");

    private String role;

    UserRole() {
    }

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
