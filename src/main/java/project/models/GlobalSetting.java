package project.models;

import lombok.Data;
import project.models.enums.Decision;
import project.models.enums.Settings;

import javax.persistence.*;

/**
 * Сущность для глобальных настроек движка
 */
@Data
@Entity
@Table(name = "global_setting")
public class GlobalSetting {
    /**
     * Уникальный ключ, генерируется автоматически
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Для настройки
     */
    @Enumerated(value = EnumType.STRING)
    private Settings code;

    /**
     * Название настройки
     */
    private String name;

    /**
     * Значение настройки
     */
    @Enumerated(value = EnumType.STRING)
    private Decision value;
}
