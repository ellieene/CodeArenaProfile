package com.example.profile.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserEditPasswordRequest(

        @Schema(description = "Old Password", example = "oldPassword")
        @NotBlank(message = "Cтарый пароль не должен быть пустым")
        String oldPassword,

        @Schema(description = "Новый пароль", example = "newPassword")
        @Size(min = 8, max = 255, message = "Длина нового пароля должна быть от 8 до 255 символов")
        @NotBlank(message = "Новый пароль не должен быть пустым")
        String newPassword
) {
}