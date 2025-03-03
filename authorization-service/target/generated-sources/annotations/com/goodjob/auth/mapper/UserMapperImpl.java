package com.goodjob.auth.mapper;

import com.goodjob.auth.dto.UserDto;
import com.goodjob.auth.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-03T12:28:23+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.roles( rolesToStrings( user.getRoles() ) );
        userDto.createdAt( user.getCreatedAt() );
        userDto.email( user.getEmail() );
        userDto.enabled( user.isEnabled() );
        userDto.firstName( user.getFirstName() );
        userDto.headline( user.getHeadline() );
        userDto.id( user.getId() );
        userDto.lastName( user.getLastName() );
        userDto.profilePictureUrl( user.getProfilePictureUrl() );
        userDto.summary( user.getSummary() );
        userDto.updatedAt( user.getUpdatedAt() );

        return userDto.build();
    }

    @Override
    public User toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder<?, ?> user = User.builder();

        user.updatedAt( userDto.getUpdatedAt() );
        user.email( userDto.getEmail() );
        user.enabled( userDto.isEnabled() );
        user.firstName( userDto.getFirstName() );
        user.headline( userDto.getHeadline() );
        user.id( userDto.getId() );
        user.lastName( userDto.getLastName() );
        user.profilePictureUrl( userDto.getProfilePictureUrl() );
        user.summary( userDto.getSummary() );

        return user.build();
    }
}
