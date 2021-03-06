package project.models;

import lombok.Data;

import javax.persistence.*;

/**
 * Сущность для связи тегов и постов
 */
@Data
@Entity
@Table(name = "tag2post")
public class TagToPost {
    /**
     * Уникальный ключ, генерируется автоматически
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Id поста
     */
    @Column(name = "post_id")
    private Integer postId;
    /**
     * Id тега
     */
    @Column(name = "tag_id")
    private Integer tagId;
}
