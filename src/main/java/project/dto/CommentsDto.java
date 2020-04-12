package project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime time;
    /**
     * Текст комментария
     */
    private String text;
    /**
     * Информация о пользователе оставивший комментарий
     */
    private UserWithPhotoInformationDto user;
}
