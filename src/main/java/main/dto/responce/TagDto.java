package main.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagDto {
    private String name;
    private Short weight;
}
