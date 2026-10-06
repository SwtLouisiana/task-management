package com.taskmanagement.taskmanager.mapper;

import com.taskmanagement.taskmanager.config.MapperConfig;
import com.taskmanagement.taskmanager.dto.user.UserRegistrationRequestDto;
import com.taskmanagement.taskmanager.dto.user.UserResponseDto;
import com.taskmanagement.taskmanager.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "memberships", ignore = true)
    User toEntity(UserRegistrationRequestDto requestDto);
    
    UserResponseDto toDto(User user);
}
