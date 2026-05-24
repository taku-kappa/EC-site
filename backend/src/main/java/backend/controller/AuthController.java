package backend.controller;

import backend.dto.request.LoginRequest;
import backend.dto.request.RegisterRequest;
import backend.dto.response.AuthResponse;
import backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * ユーザー登録
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(
            @Valid @RequestBody RegisterRequest request
    ) {
        Long userId = authService.registerUser(request);

        AuthResponse.AuthData data =
                new AuthResponse.AuthData();

        data.setUserId(userId);

        AuthResponse response =
                new AuthResponse(true, data);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * ログイン
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(
            @Valid @RequestBody LoginRequest request
    ) {
        String token =
                authService.authenticateUser(request);

        AuthResponse.AuthData data =
                new AuthResponse.AuthData();

        data.setToken(token);

        AuthResponse response =
                new AuthResponse(true, data);

        return ResponseEntity.ok(response);
    }
}