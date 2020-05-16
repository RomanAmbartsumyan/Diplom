package project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс для работы с постом
 * Все параметры беруться из БД
 */
@Data
@AllArgsConstructor
public class PostDto {
    /**
     * Id поста
     */
    private Integer id;
    /**
     * Время публикации
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime time;
    /**
     * Объект пользователя
     */
    private UserDto user;
    /**
     * Заголовок поста
     */
    private String title;
    /**
     * Текст поста
     */
    private String announce;
    /**
     * Количество лайков
     */
    private Integer likeCount;
    /**
     * Количество дизлайков
     */
    private Integer dislikeCount;
    /**
     * Количество комментариев
     */
    private Integer commentCount;
    /**
     * Количество просмотров
     */
    private Integer viewCount;
}
