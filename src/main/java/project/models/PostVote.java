package project.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Column(name = "user_id")
    private Integer userId;

    /**
     * Id поста которому поставлен лайк/дизлайк
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post postId;

    /**
     * Дата и время лайка/дизлайка
     */
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime time;

    /**
     * Значение:
     * 1 - лайк
     * -1 - дизлайк
     */
    @Column(name = "value")
    private Byte value;
}
