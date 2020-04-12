package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MyPostListDto {
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
    private String status;
}
