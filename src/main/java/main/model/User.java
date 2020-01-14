package main.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Сущность для пользователей
 */
@Data
@Entity
@Table(name = "user")
public class User {

    /**
     * Уникальный ключ, генерируется автоматически
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Имя пользователя
     */
    private String name;

    /**
     * Пароль пользователя
     */
    private String password;

    /**
     * Аватар пользователя
     */
    private String photo;

    /**
     * Дата и время регистрации
     */
    @Column(name = "reg_time")
    private Date regTime;

    /**
     * Код восстановления пароля
     */
    private String code;

    /**
     * Является ли пользователем модератером
     * 1 - является
     * 0 - не является
     */
    @Column(name = "is_moderator")
    private Byte moderator;
}
