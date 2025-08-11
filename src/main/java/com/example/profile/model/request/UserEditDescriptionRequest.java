package com.example.profile.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserEditDescriptionRequest(

        @Schema(description = "Description", example = "Описание")
        @Size(min = 10, max = 256, message = "Длина описания должна быть от 10 до 255 символов")
        @NotBlank(message = "Описание не должно быть пустым")
        String description
) {
}
