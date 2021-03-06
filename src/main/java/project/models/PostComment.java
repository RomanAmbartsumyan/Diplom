package project.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Сущность для комментариев
 */
@Data
@Entity
@Table(name = "post_comment")
public class PostComment {
    /**
     * Уникальный ключ, генерируется автоматически
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Id комментария на который оставлен комментарий
     */
    @Column(name = "parent_id")
    private Integer parenId;
    /**
     * Id поста на который оставлен комментарий
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post postId;
    /**
     * Id автора комментария
     */
    @Column(name = "user_id")
    private Integer userId;
    /**
     * Дата и время комментария
     */
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime time;

    /**
     * Текст комментария
     */
    private String text;
}
