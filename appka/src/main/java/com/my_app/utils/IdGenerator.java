package com.my_app.utils;

import java.util.List;

public class IdGenerator {
    public static <T> Integer getNextId(List<T> items, java.util.function.Function<T, Integer> idExtractor) {
        if (items.isEmpty()) {
            return 1;
        }
        return items.stream()
                .mapToInt(idExtractor::apply)
                .max()
                .orElse(0) + 1;
    }
}
