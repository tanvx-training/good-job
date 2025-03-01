package com.goodjob.auth.mapper;

import com.goodjob.auth.dto.UserDto;
import com.goodjob.auth.entity.Role;
import com.goodjob.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for converting between User entity and UserDto.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convert User entity to UserDto.
     *
     * @param user the User entity
     * @return the UserDto
     */
    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToStrings")
    UserDto toDto(User user);

    /**
     * Convert UserDto to User entity.
     *
     * @param userDto the UserDto
     * @return the User entity
     */
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "accountNonExpired", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "credentialsNonExpired", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toEntity(UserDto userDto);

    /**
     * Convert a set of Role entities to a set of role name strings.
     *
     * @param roles the set of Role entities
     * @return the set of role name strings
     */
    @Named("rolesToStrings")
    default Set<String> rolesToStrings(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
} 