package com.example.profile.service.grpcsevice;

import com.example.grpc.user.CheckRequest;
import com.example.grpc.user.CheckResponse;
import com.example.grpc.user.UserServiceGrpc;
import com.example.profile.service.user.impl.UserServiceImpl;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    private final UserServiceImpl userService;

    @Override
    public void checkUuidAndUsername(CheckRequest request, StreamObserver<CheckResponse> responseObserver) {
        boolean isValid = userService.checkOwnerUsernameAndUUID(
                request.getUsername(),
                request.getUserId()
        );

        CheckResponse response = CheckResponse.newBuilder()
                .setIsValid(isValid)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
