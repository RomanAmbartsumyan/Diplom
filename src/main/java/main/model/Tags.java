package main.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Сущность для тегов
 */
@Data
@Entity
@Table(name = "tags")
public class Tags {
    /**
     * Уникальный ключ, генерируется автоматически
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * название тега
     */
    private String name;
}