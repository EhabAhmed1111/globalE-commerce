package com.ihab.e_commerce.data.mapper;


import com.ihab.e_commerce.data.dto.UserDto;
import com.ihab.e_commerce.data.model.User;
import com.ihab.e_commerce.rest.response.vendor.VendorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {


    public UserDto fromUserToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public User fromDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build();
    }

    public VendorResponse fromUserToVendorResponse(User user) {
        return VendorResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public List<VendorResponse> fromListUsersToListVendorResponses(List<User> users) {
        return users == null ? Collections.emptyList() :
                users
                        .stream()
                        .map(this::fromUserToVendorResponse)
                        .collect(Collectors.toList());
    }
}
