package com.my_app.entities.warehouse_cell;

public enum Volume {
    LITTLE(2),
    BIG(4);

    private final int value;

    Volume(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
