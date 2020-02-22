package project.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostSearch {
    /**
     * Кол-во постов найденых по поиску
     */
    private Integer count;
    /**
     * Коллекция постов
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
    private String query;
}
