package main.dto.responce;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс для работы с пользователями
 * Все данные беруться из БД
 */
@Data
@AllArgsConstructor
public class UserDto {
    /**
     * Id пользователя
     */
    private Integer id;
    /**
     * Имя пользователя
     */
    private String name;
}
