package main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

/**
 * Сущность для постов
 */
@Data
@Entity
@Table(name = "post")
public class Post {
    /**
     * Уникальный ключ, генерируется автоматически
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /**
     * Id пользователя
     */
    @Column(name = "user_id")
    private Integer userId;
    /**
     * дата и время публикации поста
     */
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime time;
    /**
     * Заголовок поста
     */
    private String title;
    /**
     * Текст поста
     */
    private String text;
    /**
     * Кол-во просмотров
     */
    @Column(name = "view_count")
    private Integer viewCount;
    /**
     * Статус модерации
     */
    @Column(name = "moderation_status")
    private ModerationStatus moderationStatus;
    /**
     * Скрыта или активна публикация:
     * 0 - скрыта
     * 1 - активна
     */
    @Column(name = "is_active")
    private Byte active;
    /**
     * Id модератора, который проверил публикацию
     */
    @Column(name = "moderator_id")
    private Integer moderatorId;
}
