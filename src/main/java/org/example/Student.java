package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Объект студента.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    //region Поля

    /**
     * Имя студента.
     */
    private String name;

    /**
     * Возраст студента.
     */
    private int age;

    /**
     * Средний бал.
     */
    // Отключаем использование геттера,
    // что бы Jackson пропускал поле при сериализации
    @Getter(onMethod_ = @JsonIgnore)
    private transient double GPA;

    //endregion

}
