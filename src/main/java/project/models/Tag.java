package project.models;

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
     * Название тега
     */
    private String name;
}
