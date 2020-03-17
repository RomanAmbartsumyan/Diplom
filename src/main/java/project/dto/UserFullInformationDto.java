package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserFullInformationDto {
    /**
     * id пользователя
     */
    private Integer id;
    /**
     * Имя пользователя
     */
    private String name;
    /**
     * Аватар пользователя
     */
    private String photo;
    /**
     * email пользователя
     */
    private String email;
    /**
     * Яляется ли пользователь модератером
     * 1 - является
     * 0 - не является
     */
    private boolean moderation;
    /**
     * Колличество модераций
     */
    private Integer moderationCount;
    /**
     * Доступ к настройкам блога
     * true - есть доступ
     * false - если доступа нет
     */
    private boolean settings;

}
