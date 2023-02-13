package com.example.demo.controller;

import com.example.demo.dto.token.AccessTokenDTO;
import com.example.demo.dto.user.LoginDTO;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "로그인", description = "이메일과 패스워드를 사용해 로그인합니다.", tags = { "UserController" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = AccessTokenDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/v1/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO request) throws Exception {
        AccessTokenDTO token = userService.login(request.toEntity());

        return ResponseEntity.ok()
                .body(token);
    }

    @Operation(summary = "내 정보 확인", description = "액세스 토큰을 사용해 내 정보를 확인합니다.", tags = { "UserController" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/v1/me")
    public ResponseEntity myInfo(HttpServletRequest request) throws Exception {
        Long id = jwtUtil.getIdFromToken(request.getHeader("Authorization"));
        UserResponse response = UserResponse.from(userService.getInfo(id));
        return ResponseEntity.ok().body(response);
    }
}