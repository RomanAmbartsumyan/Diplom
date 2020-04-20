package project.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.Where;
import project.models.enums.ModerationStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(length=1000000)
    private String text;
    /**
     * Кол-во просмотров
     */
    @Column(name = "view_count")
    private Integer viewCount;
    /**
     * Статус модерации
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status", nullable = false, columnDefinition = "enum('NEW', 'ACCEPTED', 'DECLINED')")
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

    /**
     * Получаем данные из сущности PostVote
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "postId", cascade = CascadeType.ALL)
    @Where(clause = "value = 1")
    private List<PostVote> postVotes;

    /**
     * Получаем данные из сущности PostComment
     */
    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostComment> postComments;
}
