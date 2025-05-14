package com.my_app.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public abstract class JsonStorageService<T> {
    protected final ObjectMapper objectMapper;
    protected final String filePath;
    protected final Class<T> type;

    public JsonStorageService(String filePath, Class<T> type) {
        this.objectMapper = new ObjectMapper();
        this.filePath = filePath;
        this.type = type;
    }

    protected List<T> readFile() throws IOException {
        File file = new File(filePath);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                return new ArrayList<>();
            }
            
            if (file.length() == 0) {
                return new ArrayList<>();
            }

            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла " + filePath + ": " + e.getMessage());
            throw new IOException("Ошибка при чтении из файла: " + e.getMessage(), e);
        }
    }

    protected void writeFile(List<T> items) throws IOException {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            
            if (!file.canWrite()) {
                throw new IOException("Нет прав на запись в файл: " + filePath);
            }
            
            objectMapper.writeValue(file, items);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл " + filePath + ": " + e.getMessage());
            throw new IOException("Ошибка при записи в файл: " + e.getMessage(), e);
        }
    }
    
}
