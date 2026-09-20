package com.taskmanagement.taskmanager.security;

import com.taskmanagement.taskmanager.dto.user.UserLoginRequestDto;
import com.taskmanagement.taskmanager.dto.user.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public UserLoginResponseDto authenticate(
            UserLoginRequestDto requestDto) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                requestDto.getEmail(),
                                requestDto.getPassword()
                        )
                );
        
        String token = jwtService.generateToken(
                authentication.getName()
        );
        
        return new UserLoginResponseDto(token);
    }
}
