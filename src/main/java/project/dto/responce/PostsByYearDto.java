package project.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
public class PostsByYearDto {
    private Set<String> years;
    private Map<String, Integer> posts;
}
