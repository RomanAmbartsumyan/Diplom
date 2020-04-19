package project.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class ErrorsMessageDto {
    private boolean result = false;
    private HashMap<String, String> errors;

    public ErrorsMessageDto(HashMap<String, String> errors) {
        this.errors = errors;
    }
}
