package project.dto;

import lombok.Data;

@Data
public class ErrorsMessageDto {
    private boolean result = false;
    private ErrorsDto errors;
}
