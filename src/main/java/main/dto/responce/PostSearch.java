package main.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostSearch {
    private Integer count;
    private List<Posts> posts;
    private Integer offset;
    private Integer limit;
    private String query;
}
