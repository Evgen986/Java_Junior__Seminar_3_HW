package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.util.Optional;

/**
 * Разработайте класс Student с полями:
 * String name;
 * int age;
 * transient double GPA (средний балл);
 * Обеспечьте поддержку сериализации для этого класса.
 * Создайте объект класса Student и инициализируйте его данными.
 * Сериализуйте этот объект в файл.
 * Десериализуйте объект обратно в программу из файла.
 * Выведите все поля объекта, включая GPA, и обсудите,
 * почему значение GPA не было сохранено/восстановлено.
 */

public class Program {
    public static void main(String[] args) {
        Student student1 = new Student("Иван", 25, 4.5);
        Student student2 = new Student("Alex", 22, 4.7);

        // region дефолтные средства Java
        System.out.println(student1);
        serializeBin(student1, "student1");
        student1 = (Student) deserializeBin("student1");
        System.out.println(student1);

        // endregion

        //region Jackson

        serializeJson(student2, "student2");
        student2 = deserializeJson("student2", Student.class)
                .orElse(null);
        System.out.println(student2);

        //endregion
    }

    //region методы дефолтных сериализаций
    public static void serializeBin(Object object, String fileName) {
        try (FileOutputStream fileOutputStream =
                     new FileOutputStream(fileName + ".bin");
             ObjectOutputStream outputStream =
                     new ObjectOutputStream(fileOutputStream)
        ) {
            outputStream.writeObject(object);
        } catch (IOException e) {
            printMessage("Ошибка сериализации файла: " + e.getMessage());
        }
    }

    public static Object deserializeBin(String fileName) {
        try (FileInputStream fileInputStream =
                     new FileInputStream(fileName + ".bin");
             ObjectInputStream inputStream =
                     new ObjectInputStream(fileInputStream)
        ) {
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            printMessage("Ошибка десериализации объекта: " +
                    e.getMessage());
        }
        return null;
    }
    //endregion

    //region методы с использование Jackson
    public static void serializeJson(Object object, String fileName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT,
                    true);
            objectMapper.writeValue(new File(fileName + ".json"),
                    object);
        } catch (IOException e) {
            printMessage("Ошибка сериализации файла: " + e.getMessage());
        }
    }

    public static <T> Optional<T> deserializeJson(
            String fileName, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String filePath = System.getProperty("user.dir") +
                File.separator +
                fileName +
                ".json";
        try (InputStream inputStream = new FileInputStream(filePath)) {
            return Optional.of(
                    objectMapper.readValue(inputStream, clazz));
        } catch (JsonProcessingException e) {
            printMessage("Ошибка десериализации объекта: " +
                    e.getMessage());
        } catch (IOException e) {
            printMessage("Ошибка чтения файла: " + e.getMessage());
        }
        return Optional.empty();
    }
    //endregion

    private static void printMessage(String message) {
        System.out.println(message);
    }
}