package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserWithPhotoInformationDto {
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
