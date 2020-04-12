package project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
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
     * Кол-вол клмментариев
     */
    private Integer commentCount;
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
    private List<String> tags;
}
