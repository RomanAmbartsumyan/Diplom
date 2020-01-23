package main.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class PostList {
    private Integer count;
    private Set<Posts> posts;
    private Integer limit;
    private Integer offset;
    private String mode;
}
