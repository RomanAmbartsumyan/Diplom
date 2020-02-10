package main.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserFullInformation {
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
