package com.example.profile.controller;

import com.example.profile.model.dto.UserDTO;
import com.example.profile.model.projection.UserRating;
import com.example.profile.model.request.UserEditRequest;
import com.example.profile.model.responce.StringResponse;
import com.example.profile.service.user.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
@Tag(name = "UserController")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Operation(summary = "Профиль пользователя")
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserDTO> getUser(@Valid @PathVariable UUID userId){
        return ResponseEntity.ok(userServiceImpl.getUser(userId));
    }

//    @Operation(summary = "Изменения пользователя")
//    @PutMapping("/profile/edit-user/{userId}")
//    public ResponseEntity<StringResponse> editUser(@Valid @PathVariable UUID userId, @RequestBody UserEditRequest userEditRequest){
//        return ResponseEntity.ok(userServiceImpl.editUser(userId, userEditRequest));
//    }

    @Operation(summary = "Получение списка пользователей по рейтингу")
    @GetMapping("/rating")
    public ResponseEntity<Collection<UserRating>> getRatingUser(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size) {
        return ResponseEntity.ok(userServiceImpl.getRatingUser(page, size));
    }
}
