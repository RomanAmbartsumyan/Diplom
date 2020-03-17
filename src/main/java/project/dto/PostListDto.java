package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Класс для работы со списком постов
 */
@Data
@AllArgsConstructor
public class PostListDto {
    /**
     * Количество постов
     */
    private Integer count;
    /**
     * Список постов
     */
    private List<PostDto> posts;
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
