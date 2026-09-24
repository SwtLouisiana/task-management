package com.taskmanagement.taskmanager.service.impl;

import com.taskmanagement.taskmanager.dto.user.UserProfileUpdateRequestDto;
import com.taskmanagement.taskmanager.dto.user.UserRegistrationRequestDto;
import com.taskmanagement.taskmanager.dto.user.UserResponseDto;
import com.taskmanagement.taskmanager.dto.user.UserRoleUpdateRequestDto;
import com.taskmanagement.taskmanager.exception.LastAdminException;
import com.taskmanagement.taskmanager.exception.RegistrationException;
import com.taskmanagement.taskmanager.exception.UserNotFoundException;
import com.taskmanagement.taskmanager.exception.UsernameAlreadyExistsException;
import com.taskmanagement.taskmanager.mapper.UserMapper;
import com.taskmanagement.taskmanager.models.User;
import com.taskmanagement.taskmanager.models.enums.Role;
import com.taskmanagement.taskmanager.repository.UserRepository;
import com.taskmanagement.taskmanager.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional
    public UserResponseDto registerUser(UserRegistrationRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new RegistrationException(
                    "Username is already registered: " + requestDto.getUsername()
            );
        }
        
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException(
                    "Email is already registered: " + requestDto.getEmail()
            );
        }
        
        User user = userMapper.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        
        return userMapper.toDto(user);
    }
    
    @Override
    @Transactional
    public UserResponseDto updateUserProfile(
            String email,
            UserProfileUpdateRequestDto requestDto) {
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found by email: " + email
                ));
        
        if (requestDto.getUsername() != null) {
            String username = requestDto.getUsername();
            
            if (!username.equals(user.getUsername())) {
                if (userRepository.existsByUsername(username)) {
                    throw new UsernameAlreadyExistsException(
                            "Username is already taken: " + username
                    );
                }
                user.setUsername(username);
            }
        }
        
        if (requestDto.getFirstName() != null) {
            user.setFirstName(requestDto.getFirstName());
        }
        
        if (requestDto.getLastName() != null) {
            user.setLastName(requestDto.getLastName());
        }
        
        return userMapper.toDto(user);
    }
    
    @Override
    @Transactional
    public UserResponseDto updateUserRole(
            Long userId,
            UserRoleUpdateRequestDto requestDto) {
        
        List<User> admins = userRepository.findAllByRoleForUpdate(Role.ADMIN);
        
        User user = admins.stream()
                .filter(admin -> admin.getId().equals(userId))
                .findFirst()
                .orElseGet(() -> userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException(
                                "User not found by id: " + userId
                        )));
        
        boolean isLastAdmin = admins.size() == 1
                && admins.get(0).getId().equals(userId);
        
        if (isLastAdmin && requestDto.getRole() == Role.USER) {
            throw new LastAdminException(
                    "Cannot demote the last administrator"
            );
        }
        
        user.setRole(requestDto.getRole());
        
        return userMapper.toDto(user);
    }
}
