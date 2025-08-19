package com.example.profile.model.dto;

import com.example.grpc.profileAndArticle.FavoriteArticle;
import com.example.profile.model.enums.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private String username;
    private String email;
    private String description;
    private List<FavoriteArticleDTO> favorites;
    private Role role;
    private int points;
    private boolean owner;
}
