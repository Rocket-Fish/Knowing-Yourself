package com.knowyourself;

public enum Acts {
    A1("A1"),
    A2("A2"),
    A3("A3"),
    A4("A4"),
    B1("B1"),
    B2("B2"),
    B3("B3"),
    C1("C1"),
    C2("C2"),
    D1("D1"),
    D2("D2"),
    D3("D3"),
    D4("D4"),
    E1("E1"),
    E2("E2"),
    E3("E3"),
    E4("E4"),
    F1("F1"),
    F2("F2"),
    G1("G1"),
    G2("G2"),
    G3("G3"),
    G4("G4"),
    H1("H1"),
    H2("H2"),
    H3("H3"),
    I1("I1"),
    I2("I2"),
    I3("I3"),
    J1("J1"),
    J2("J2"),
    J3("J3"),
    K1("K1"),
    K2("K2"),
    L1("L1")
    ;

    private String name;
    Acts(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
