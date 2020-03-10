package project.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CaptchaDto {
    private String secret;
    private String image;
}
