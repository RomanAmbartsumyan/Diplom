package project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllStatisticsDto {
    @JsonProperty("Постов")
    private Integer postCount;
    @JsonProperty("Лайков")
    private Integer likesCount;
    @JsonProperty("Дизлайков")
    private Integer dislikesCount;
    @JsonProperty("Просмотров")
    private Integer viewCount;
    @JsonProperty("Первая публикация")
    private String firstPublication;
}
