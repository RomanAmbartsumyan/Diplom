package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsDto {
    private Integer postCount;
    private Integer likesCount;
    private Integer dislikesCount;
    private Integer viewCount;
    private String firstPublication;
}
