package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private MultipartFile photo;
    private Integer removePhoto;
    private String name;
    private String email;
    private String password;
}
