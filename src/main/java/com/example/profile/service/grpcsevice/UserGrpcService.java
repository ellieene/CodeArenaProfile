package com.example.profile.service.grpcsevice;

import com.example.grpc.user.*;
import com.example.profile.exception.EntityNotFoundException;
import com.example.profile.model.entity.User;
import com.example.profile.repository.UserRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import static com.example.profile.util.CommonStrings.NOT_FOUND_USER;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;

    @Override
    public void getPointsAndUserId(PointsRequest request, StreamObserver<PointsResponse> responseObserver) {
        try {
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(()-> new EntityNotFoundException(NOT_FOUND_USER));
            PointsResponse response = PointsResponse.newBuilder()
                    .setUserId(user.getId().toString())
                    .setPoints(user.getPoints())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            // Остальные ошибки логируем и возвращаем INTERNAL
            log.error("Error in getUsernameByUuid", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Internal server error")
                    .asRuntimeException());
        }
    }

    @Override
    public void purchasingAnArticle(PurchasingRequest request, StreamObserver<PurchasingResponse> responseObserver){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new EntityNotFoundException(NOT_FOUND_USER));
        user.setPoints(request.getPoints());
        userRepository.save(user);
        PurchasingResponse response = PurchasingResponse.newBuilder()
                .setPurchasing(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
