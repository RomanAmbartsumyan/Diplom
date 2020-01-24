package main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Сущность для лайков и дизлайков
 */
@Data
@Entity
@Table(name = "post_vote")
public class PostVote {

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
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime time;

    /**
     * Значение:
     * 1 - лайк
     */
    @Column(name = "like_count")
    private Byte likeCount;

    /**
     * Значение:
     * 1 - лайк
     */
    @Column(name = "dislike_count")
    private Byte dislikeCount;
}
