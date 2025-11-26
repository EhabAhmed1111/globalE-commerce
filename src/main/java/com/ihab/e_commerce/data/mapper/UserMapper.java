package com.ihab.e_commerce.data.mapper;


import com.ihab.e_commerce.data.dto.UserDto;
import com.ihab.e_commerce.data.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {


    public UserDto fromUserToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getFirstName() + user.getLastName())
                .build();
    }
}
