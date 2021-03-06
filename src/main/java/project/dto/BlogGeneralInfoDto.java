package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс для работы с футером
 */
@Data
@AllArgsConstructor
public class BlogGeneralInfoDto {
    /**
     * Заголовок сайта
     */
    private String title;
    /**
     * Тематика сайта
     */
    private String subtitle;
    /**
     * Номер телефона
     */
    private String phone;
    /**
     * e-mail адрес
     */
    private String email;
    /**
     *
     */
    private String copyright;
    /**
     *
     */
    private String copyrightFrom;

}
