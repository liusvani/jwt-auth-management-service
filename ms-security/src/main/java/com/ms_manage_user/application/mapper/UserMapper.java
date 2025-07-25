package com.ms_manage_user.application.mapper;

import com.ms_manage_user.application.dto.input.UserRequest;
import com.ms_manage_user.application.dto.output.UserResponse;
import com.ms_manage_user.domain.model.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UserMapper {


    User toEntity(UserRequest request);


    UserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromRequest(UserRequest request, @MappingTarget User user);
}




