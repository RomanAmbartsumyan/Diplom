package main.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Сущность для глобальных настроек движка
 */
@Data
@Entity
@Table(name = "global_settings")
public class GlobalSettings {

    /**
     * Уникальный ключ, генерируется автоматически
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Для настройки
     */
    private String code;

    /**
     * Название настройки
     */
    private String name;

    /**
     * Значение настройки
     */
    private String value;
}