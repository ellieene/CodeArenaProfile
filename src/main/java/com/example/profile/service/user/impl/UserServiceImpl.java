package com.example.profile.service.user.impl;

import com.example.profile.exception.EntityNotFoundException;
import com.example.profile.exception.InvalidCredentialsException;
import com.example.profile.model.dto.UserDTO;
import com.example.profile.model.entity.User;
import com.example.profile.model.projection.UserRating;
import com.example.profile.model.request.UserEditDescriptionRequest;
import com.example.profile.model.request.UserEditPasswordRequest;
import com.example.profile.model.response.StringResponse;
import com.example.profile.repository.UserRepository;
import com.example.profile.service.user.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collection;
import java.util.UUID;

import static com.example.profile.util.CommonStrings.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUser(String username, String userId) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_USER));
        UserDTO userDTO = new UserDTO();
        modelMapper.map(user, userDTO);
        userDTO.setOwner(checkOnwerUser(user.getId(), userId));
        return userDTO;
    }

    @Transactional
    @Override
    public StringResponse editUserDescription(UUID userId, UserEditDescriptionRequest userEditDescriptionRequest) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_USER));
        user.setDescription(userEditDescriptionRequest.description());
        userRepository.save(user);

        return new StringResponse(EDIT_USER_SUCCESSES);
    }

    @Transactional
    @Override
    public StringResponse editUserPassword(UUID userId, UserEditPasswordRequest userEditPasswordRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_USER));
        if (!passwordEncoder.matches(userEditPasswordRequest.oldPassword(), user.getPassword())){
            throw new InvalidCredentialsException(UNCORRECT_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(userEditPasswordRequest.newPassword()));
        userRepository.save(user);
        return new StringResponse(EDIT_PASSWORD_USER_SUCCESSES);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<UserRating> getRatingUser(Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page, size);
        return userRepository.findAllByOrderByPointsDesc(pageable);
    }

    private boolean checkOnwerUser(UUID userId, String userIdHeader){
        return userId.equals(UUID.fromString(userIdHeader));
    }
}
