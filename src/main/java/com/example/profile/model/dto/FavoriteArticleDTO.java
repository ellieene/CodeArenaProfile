package com.example.profile.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteArticleDTO {
    private String username;
    private UUID articleId;
    private String title;
    private int price;
}