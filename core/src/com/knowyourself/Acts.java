package com.knowyourself;

public enum Acts {
    A1("A1"),
    A2("A2");

    private String name;
    Acts(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
