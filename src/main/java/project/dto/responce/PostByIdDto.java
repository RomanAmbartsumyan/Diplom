package project.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PostByIdDto {
    /**
     * id поста
     */
    private Integer id;
    /**
     * Дата и время поста
     */
    private LocalDateTime time;
    /**
     * Информация о пользователе оставивший пост
     */
    private UserDto user;
    /**
     * Загаловок поста
     */
    private String title;
    /**
     * Текст поста
     */
    private String text;
    /**
     * Кол-во лайков поставленных на пост
     */
    private Byte likeCount;
    /**
     * Кол-во дизлайков поставленных на пост
     */
    private Byte dislikeCount;
    /**
     * Кол-во просмотров
     */
    private Integer viewCount;
    /**
     * Коллекция комментариев к посту
     */
    private List<CommentsDto> comments;
    /**
     * Коллекция тегов
     */
    private List<String> tag;
}
