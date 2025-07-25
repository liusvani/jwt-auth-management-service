package com.ms_manage_user.application.mapper;

import com.ms_manage_user.application.dto.input.UserRequest;
import com.ms_manage_user.application.dto.output.UserResponse;
import com.ms_manage_user.domain.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-25T09:45:07-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        return user;
    }

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        return userResponse;
    }

    @Override
    public void updateUserFromRequest(UserRequest request, User user) {
        if ( request == null ) {
            return;
        }
    }
}
