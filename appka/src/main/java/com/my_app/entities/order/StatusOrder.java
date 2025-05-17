package com.my_app.entities.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusOrder {
    IN_WAREHOUSE("в обработке"),
    IN_PATH("отправлен"),
    IN_SALE_POINT("доставлен"),
    IN_CUSTOMER("получен");

    private final String string;

    StatusOrder(String string) {
        this.string = string;
    }

    @JsonValue
    public String getString() {
        return string;
    }

    @JsonCreator
    public static StatusOrder fromString(String text) {
        for (StatusOrder status : StatusOrder.values()) {
            if (status.string.equals(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Неизвестный статус: " + text);
    }
}
