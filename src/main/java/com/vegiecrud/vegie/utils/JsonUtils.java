package com.vegiecrud.vegie.utils;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {
    private static final Gson gson = new Gson();

    private JsonUtils() {
        // Private constructor to prevent instantiation
    }

    public static <T> String toJson(T obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
