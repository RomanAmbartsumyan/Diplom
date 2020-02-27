package project.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentsDto {
    /**
     * id комментария
     */
    private Integer id;
    /**
     * Время комментария
     */
    private LocalDateTime time;
    /**
     * Информация о пользователе оставивший комментарий
     */
    private UserWithPhotoInformation user;
    /**
     * Текст комментария
     */
    private String text;
}
