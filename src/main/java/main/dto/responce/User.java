package main.dto.responce;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonRootName(value = "user")
public class User {
    private Integer id;
    private String name;
}
