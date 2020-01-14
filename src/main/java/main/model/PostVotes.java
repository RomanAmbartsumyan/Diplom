package main.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Сущьность для лайков и дизлайков
 */
@Data
@Entity
@Table(name = "post_votes")
public class PostVotes {

    /**
     * Уникальный ключ, генерируется автоматически
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Id пользователя кто поставил лайк/дизлайк
     */
    @Column(name =  "user_id")
    private Integer userId;

    /**
     * Id поста которому поставлен лайк/дизлайк
     */
    @Column(name = "post_id")
    private Integer postId;

    /**
     * Дата и время лайка/дизлайка
     */
    private Date time;

    /**
     * Значение:
     * 1 - лайк
     * -1 - дизлайк
     */
    private Byte value;
}