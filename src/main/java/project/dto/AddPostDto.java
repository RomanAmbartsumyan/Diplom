package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddPostDto {
    private String time;
    private byte active;
    private String title;
    private String text;
    private String tags;
}
