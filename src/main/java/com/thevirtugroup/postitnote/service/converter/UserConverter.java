package com.thevirtugroup.postitnote.service.converter;

import com.thevirtugroup.postitnote.dto.UserResponseDto;
import com.thevirtugroup.postitnote.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());

        return dto;
    }
}
