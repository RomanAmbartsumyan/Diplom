package main.models;

import lombok.Data;

import javax.persistence.*;

/**
 * Сущность для тегов
 */
@Data
@Entity
@Table(name = "tag")
public class Tag {
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
