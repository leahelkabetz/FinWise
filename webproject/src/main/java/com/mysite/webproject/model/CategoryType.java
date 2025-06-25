package com.mysite.webproject.model;

public enum CategoryType {
    INCOME("הכנסה"),
    EXPENSE("הוצאה");

    private final String label;

    CategoryType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
