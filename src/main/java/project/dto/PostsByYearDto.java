package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class PostsByYearDto {
    private List<String> years;
    private Map<String, Integer> posts;
}
