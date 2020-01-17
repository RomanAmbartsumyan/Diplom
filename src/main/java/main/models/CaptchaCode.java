package main.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Сущность для капчи
 */
@Data
@Entity
@Table(name = "captcha_code")
public class CaptchaCode {

    /**
     * Уникальный ключ, генерируется автоматически
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Дата и время генерации капчи
     */
    private Date time;

    /**
     * Код отображаемы на картинке
     */
    private Byte code;

    /**
     * Код передаваемый в параметре
     */
    @Column(name = "secret_code")
    private Byte secretCode;
}
