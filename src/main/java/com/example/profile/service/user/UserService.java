package com.example.profile.service.user;

import com.example.profile.model.dto.UserDTO;
import com.example.profile.model.projection.UserRating;
import com.example.profile.model.request.UserEditRequest;
import com.example.profile.model.responce.StringResponse;

import java.util.Collection;
import java.util.UUID;

/**
 * Service to the user
 */
public interface UserService {

    /**
     * Получение пользователя
     *
     */
    UserDTO getUser(UUID userId);

//    /**
//     * Изменение пользователя
//     */
//    StringResponse editUser(UUID userId, UserEditRequest userEditRequest);

    /**
     * Получения пользователей по рейтингу поинтов
     * @param page
     * @param size
     * @return Collection {@link UserRating}
     */
    Collection<UserRating> getRatingUser(Integer page, Integer size);
}
