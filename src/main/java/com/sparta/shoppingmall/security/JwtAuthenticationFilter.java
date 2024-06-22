package com.sparta.shoppingmall.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.shoppingmall.base.dto.CommonResponse;
import com.sparta.shoppingmall.jwt.JwtProvider;
import com.sparta.shoppingmall.jwt.RefreshTokenService;
import com.sparta.shoppingmall.user.dto.LoginRequestDTO;
import com.sparta.shoppingmall.user.entity.User;
import com.sparta.shoppingmall.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;

    private final UserRepository userRepository;

    private RefreshTokenService refreshTokenService;


    public JwtAuthenticationFilter(JwtProvider jwtProvider, UserRepository userRepository,RefreshTokenService refreshTokenService) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDTO requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDTO.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
//    log.error("username = {}",username);
        String accessToken = jwtProvider.createAccessToken(username);
        String refreshToken = jwtProvider.createRefreshToken(username);
        log.info("제발 좀 되라");
        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        user.refreshTokenReset(refreshToken);
        log.info("제발 좀 되라");
       userRepository.save(user);
        log.info("제발 좀 되라");


        // 응답 헤더에 토큰 추가
        response.addHeader(JwtProvider.AUTHORIZATION_HEADER, accessToken);
        response.addHeader(JwtProvider.REFRESH_HEADER, refreshToken);

        // JSON 응답 작성
        writeJsonResponse(response, HttpStatus.OK, "로그인에 성공했습니다.", authResult.getName());

        log.info("User = {}, message = {}", username, "로그인에 성공했습니다.");
    }

    private void writeJsonResponse(HttpServletResponse response, HttpStatus status, String message, String data) throws IOException {
        CommonResponse<String> responseMessage = CommonResponse.<String>builder()
                .statusCode(status.value())
                .message(message)
                .data(data)
                .build();

        String jsonResponse = new ObjectMapper().writeValueAsString(responseMessage);
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(400);
        response.setContentType("application/json; charset=UTF-8");
        try {
            response.getWriter().write("{\"message\":\"회원을 찾을 수 없습니다.\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
