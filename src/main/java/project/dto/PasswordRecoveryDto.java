package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс для восстановления пароля
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRecoveryDto {
    /**
     * email пользователя
     */
    private String email;
}
