package main.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Класс для работы со списком постов
 */
@Data
@AllArgsConstructor
public class PostList {
    /**
     * Количество постов
     */
    private Integer count;
    /**
     * Список постов
     */
    private List<Posts> posts;
    /**
     * Параметр передается с фронта
     */
    private Integer offset;
    /**
     * Параметр передается с фронта
     */
    private Integer limit;
    /**
     * Параметр передается с фронта
     */
    private String mode;
}
