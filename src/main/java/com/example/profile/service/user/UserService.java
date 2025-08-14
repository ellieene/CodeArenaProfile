package com.example.profile.service.user;

import com.example.profile.model.dto.UserDTO;
import com.example.profile.model.projection.UserRating;
import com.example.profile.model.request.UserEditDescriptionRequest;
import com.example.profile.model.request.UserEditPasswordRequest;
import com.example.profile.model.response.StringResponse;

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
    UserDTO getUser(String username, String userId);

    /**
     * Изменение описаня пользователя
     */
    StringResponse editUserDescription(UUID userId, UserEditDescriptionRequest userEditDescriptionRequest);

    /**
     * Изменения пароля пользователся
     */
    StringResponse editUserPassword(UUID userId, UserEditPasswordRequest userEditPasswordRequest);

    /**
     * Получения пользователей по рейтингу поинтов
     * @param page
     * @param size
     * @return Collection {@link UserRating}
     */
    Collection<UserRating> getRatingUser(Integer page, Integer size);
}
