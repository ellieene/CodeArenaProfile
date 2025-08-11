package com.example.profile.service.user.impl;

import com.example.profile.exception.EntityNotFoundException;
import com.example.profile.model.dto.UserDTO;
import com.example.profile.model.entity.User;
import com.example.profile.model.projection.UserRating;
import com.example.profile.model.request.UserEditRequest;
import com.example.profile.model.responce.StringResponse;
import com.example.profile.repository.UserRepository;
import com.example.profile.service.user.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.Collection;
import java.util.UUID;

import static com.example.profile.util.CommonStrings.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

//    private final JwtTokenProvider jwtTokenProvider;
//    private final SecurityUtil securityUtil;    private final HttpServletRequest request;

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUser(UUID userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_USER));
        UserDTO userDTO = new UserDTO();
        modelMapper.map(user, userDTO);
        return userDTO;
    }

//    @Transactional
//    @Override
//    public StringResponse editUser(UUID userId, UserEditRequest userEditRequest) {
//        User user = userRepository
//                .findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_USER));
//        String token = securityUtil.extractToken(request);
//        String emailFromToken = jwtTokenProvider.extractEmail(token);
//
//        if (!user.getEmail().equals(emailFromToken)) {
//            throw new AccessDeniedException(ACCESS_DENIED);
//        }
//        user.setDescription(userEditRequest.description());
//        userRepository.save(user);
//
//        return new StringResponse(EDIT_USER_SUCCESSES);
//    }

    @Transactional(readOnly = true)
    @Override
    public Collection<UserRating> getRatingUser(Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page, size);
        return userRepository.findAllByOrderByPointsDesc(pageable);
    }
}
