package project.dto.responce;

import lombok.Data;

@Data
public class AuthUser {
    /**
     * Результат авторизации всегда true
     */
    private boolean result = true;
    /**
     * Информация о пользователе
     */
    private UserFullInformation user;

    /**
     * Конструктор класса
     */
    public AuthUser(UserFullInformation user) {
        this.user = user;
    }
}
