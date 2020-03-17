package project.dto;

import lombok.Data;

@Data
public class AuthUserDto {
    /**
     * Результат авторизации всегда true
     */
    private boolean result = true;
    /**
     * Информация о пользователе
     */
    private UserFullInformationDto user;

    /**
     * Конструктор класса
     */
    public AuthUserDto(UserFullInformationDto user) {
        this.user = user;
    }
}
