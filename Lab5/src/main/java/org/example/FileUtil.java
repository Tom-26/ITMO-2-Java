package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FileUtil {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
            .create();

    /*public static void writeFile(String filePath, String content) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
            bos.write(bytes);
            bos.flush();
        }
    }*/

    public static String readFile(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                contentBuilder.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
            }
        }
        return contentBuilder.toString();
    }

    public static <T> void writeToJson(String filePath, T objectToWrite) throws IOException {
        String json = gson.toJson(objectToWrite);
        writeFile(filePath, json);
    }

    private static void writeFile(String filePath, String json) {
    }

    public static <T> T readFromJson(String filePath, Class<T> classOfT) throws IOException {
        String json = readFile(filePath);
        return gson.fromJson(json, classOfT);
    }

    static class ZonedDateTimeAdapter extends TypeAdapter<ZonedDateTime> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

        @Override
        public void write(JsonWriter out, ZonedDateTime value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value.format(formatter));
            }
        }

        @Override
        public ZonedDateTime read(JsonReader in) throws IOException {
            String dateTimeString = in.nextString();
            return ZonedDateTime.parse(dateTimeString, formatter);
        }
    }
}
