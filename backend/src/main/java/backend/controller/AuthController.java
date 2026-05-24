package backend.controller;

import backend.dto.response.AuthResponse;
import backend.dto.request.LoginRequest;
import backend.dto.request.RegisterRequest;
import backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * ユーザー登録 API
     * [POST] /auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterRequest request) {

        // 1. Service層に処理を委譲し、登録されたユーザーのIDを受け取る
        Long userId = authService.registerUser(request);

        // 2. API設計書に基づき、レスポンスDTOを組み立てる
        AuthResponse response = new AuthResponse();
        response.setSuccess(true);

        AuthResponse.AuthData data = new AuthResponse.AuthData();
        data.setUserId(userId);
        response.setData(data);

        // 3. 201 Created と共にレスポンスを返却
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * ログイン API
     * [POST] /auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest request) {

        // 1. Service層に処理を委譲し、生成されたJWTトークンを受け取る
        String token = authService.authenticateUser(request);

        // 2. API設計書に基づき、レスポンスDTOを組み立てる
        AuthResponse response = new AuthResponse();
        response.setSuccess(true);

        AuthResponse.AuthData data = new AuthResponse.AuthData();
        data.setToken(token);
        response.setData(data);

        // 3. 200 OK と共にレスポンスを返却
        return ResponseEntity.ok(response);
    }
}