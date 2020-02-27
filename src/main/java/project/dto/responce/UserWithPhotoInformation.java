package project.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserWithPhotoInformation {
    /**
     * Id пользователя
     */
    private Integer id;
    /**
     * Имя пользователя
     */
    private String name;
    /**
     * Фотография пользователя
     */
    private String photo;
}
