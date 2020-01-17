package main.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlogGeneralInfiDto {

    String title;
    String subtitle;
    String phone;
    String email;
    String copyright;
    String copyrightFrom;

}
