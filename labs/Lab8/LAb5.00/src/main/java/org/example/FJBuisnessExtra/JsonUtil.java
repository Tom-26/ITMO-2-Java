package org.example.FJBuisnessExtra;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder().create();

    // Метод для сериализации объекта в JSON-строку
    public static <T> String toJson(T object) {
        return gson.toJson(object);  // возвращает объект в json представлении
    }

    // Метод для десериализации JSON-строки в объект заданного класса
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    // Добавленный метод для десериализации JSON-строки в объект заданного типа
    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }
}
