package project.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Дата и время генерации капчи
     */
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime time;

    /**
     * Код отображаемы на картинке
     */
    private String code;

    /**
     * Код передаваемый в параметре
     */
    @Column(name = "secret_code")
    private String secretCode;
}
