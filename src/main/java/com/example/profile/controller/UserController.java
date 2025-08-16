package com.example.profile.controller;

import com.example.profile.model.dto.UserDTO;
import com.example.profile.model.projection.UserRating;
import com.example.profile.model.request.UserEditDescriptionRequest;
import com.example.profile.model.request.UserEditPasswordRequest;
import com.example.profile.model.response.StringResponse;
import com.example.profile.service.user.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Code Arena Profile")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Operation(summary = "Профиль пользователя")
    @GetMapping(value = "/profile/{username}")
    public ResponseEntity<UserDTO> getUser(
            @Valid @PathVariable String username,
            @RequestHeader(value = "username", required = false) String usernameHeader) {

        return ResponseEntity.ok(userServiceImpl.getUser(username, usernameHeader));
    }

    @Operation(summary = "Получение списка пользователей по рейтингу")
    @GetMapping(value = "/rating")
    public ResponseEntity<Collection<UserRating>> getRatingUser(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size) {
        return ResponseEntity.ok(userServiceImpl.getRatingUser(page, size));
    }

    @Operation(summary = "Изменения пользователя")
    @PutMapping(value = "/profile/edit-user-description/{userId}")
    public ResponseEntity<StringResponse> editUserDescription(@PathVariable UUID userId, @Valid @RequestBody UserEditDescriptionRequest userEditDescriptionRequest) {
        return ResponseEntity.ok(userServiceImpl.editUserDescription(userId, userEditDescriptionRequest));
    }

    @Operation(summary = "Изменения пароля у пользователя")
    @PutMapping(value = "/profile/edit-user-password/{userId}")
    public ResponseEntity<StringResponse> editUserPassword(@PathVariable UUID userId, @Valid @RequestBody UserEditPasswordRequest userEditPasswordRequest) {
        return ResponseEntity.ok(userServiceImpl.editUserPassword(userId, userEditPasswordRequest));
    }

//    @Operation(summary = "Получения поле username по UUID (Для Code Arena Article)")
//    @GetMapping("/check-uuid-and-username")
//    public ResponseEntity<> getUserByUsername(
//            @RequestHeader("username") String username) {
//        return ResponseEntity.ok(
//                userServiceImpl.getUserByUsername(username)
//        );
//    }
}
