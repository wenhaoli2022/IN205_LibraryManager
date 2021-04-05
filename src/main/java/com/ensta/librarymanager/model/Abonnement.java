package com.ensta.librarymanager.model;

public enum Abonnement {
	BASIC(1, "basic"),
    PREMIUM(2, "premium"),
    VIP(3, "vip");

    private int value;
    private String name;

    private Abonnement(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int valueOf() {
        return this.value;
    }
}