package com.example.profile.client;

import com.example.grpc.profileAndArticle.*;
import com.example.profile.exception.EntityNotFoundException;
import com.example.profile.model.dto.FavoriteArticleDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ArticleGrpcClient {

    private final String articleServiceAddress = "codearena-article:50052";
    private final AtomicReference<UserServiceGrpc.UserServiceBlockingStub> lazyStub = new AtomicReference<>();

    private UserServiceGrpc.UserServiceBlockingStub getStub() {
        if (lazyStub.get() == null) {
            synchronized (lazyStub) {
                if (lazyStub.get() == null) {
                    ManagedChannel channel = ManagedChannelBuilder
                            .forTarget(articleServiceAddress)
                            .usePlaintext()
                            .build();
                    lazyStub.set(UserServiceGrpc.newBlockingStub(channel));
                }
            }
        }
        return lazyStub.get();
    }

    public List<FavoriteArticleDTO> GetArticleFromFavoritesByUser(UUID userId) {
        try {
            FavoriteRequest request = FavoriteRequest.newBuilder()
                    .setUserId(userId.toString())
                    .build();

            FavoriteResponse response = getStub().getArticleFromFavoritesByUser(request);

            // Преобразуем gRPC объекты в DTO
            return response.getArticlesList().stream()
                    .map(grpcArticle -> new FavoriteArticleDTO(
                            grpcArticle.getUsername(),
                            UUID.fromString(grpcArticle.getArticleId()),
                            grpcArticle.getTitle(),
                            grpcArticle.getPrice()
                    ))
                    .collect(Collectors.toList());

        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode().name().equals("NOT_FOUND")) {
                throw new EntityNotFoundException("User not found");
            }
            throw new RuntimeException("Failed to get favorite articles", e);
        }
    }

}