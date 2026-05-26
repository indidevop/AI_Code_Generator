package com.springboot.AI_Code_Generator.mapper;

import com.springboot.AI_Code_Generator.dto.auth.SignupRequest;
import com.springboot.AI_Code_Generator.dto.auth.UserProfileResponse;
import com.springboot.AI_Code_Generator.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User signupRequestToUser(SignupRequest signupRequest);

    UserProfileResponse userToUserProfileResponse(User user);
}
