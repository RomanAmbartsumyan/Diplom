package main.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Сущность для комментариев
 */
@Data
@Entity
@Table(name = "post_comments")
public class PostComments {

    /**
     * Уникальный ключ, генерируется автоматически
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Id комментария на который оставлен комментарий
     */
    @Column(name = "parent_id")
    private Integer parenId;

    /**
     * Id поста на который оставлен комментарий
     */
    @Column(name = "post_id")
    private Integer postId;

    /**
     * Id автора комментария
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * Дата и время комментария
     */
    private Date time;
}
