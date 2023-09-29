package com.thevirtugroup.postitnote.security;

import com.thevirtugroup.postitnote.dto.UserResponseDto;

class LoginResponse {

    private boolean success;

    private UserResponseDto user;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }
}