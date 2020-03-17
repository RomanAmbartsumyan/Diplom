package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ModerationPostsDto {
    private Integer count;

    private List<PostsOnModerationDto> posts;
}
