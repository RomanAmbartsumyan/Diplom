package main.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Сущность для постов
 */
@Data
@Entity
@Table(name = "posts")
public class Posts {

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
    private Date time;

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
    @ElementCollection(targetClass = ModerationStatus.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<ModerationStatus> moderationStatuses;

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
