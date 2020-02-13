package main.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class TagsDto {
    private List<TagDto> tags;
}
