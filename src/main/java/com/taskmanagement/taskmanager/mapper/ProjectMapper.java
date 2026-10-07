package com.taskmanagement.taskmanager.mapper;

import com.taskmanagement.taskmanager.config.MapperConfig;
import com.taskmanagement.taskmanager.dto.project.ProjectCreateRequestDto;
import com.taskmanagement.taskmanager.dto.project.ProjectResponseDto;
import com.taskmanagement.taskmanager.dto.project.ProjectUpdateRequestDto;
import com.taskmanagement.taskmanager.models.Project;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class)
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "memberships", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    Project toEntity(ProjectCreateRequestDto requestDto);

    @Mapping(target = "ownerId", source = "owner.id")
    ProjectResponseDto toDto(Project project);
    
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "memberships", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    void updateProject(
            ProjectUpdateRequestDto requestDto,
            @MappingTarget Project project);
}
