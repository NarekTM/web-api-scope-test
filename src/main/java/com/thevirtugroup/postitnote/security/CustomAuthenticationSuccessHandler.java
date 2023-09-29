package com.thevirtugroup.postitnote.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thevirtugroup.postitnote.dto.UserResponseDto;
import com.thevirtugroup.postitnote.model.User;
import com.thevirtugroup.postitnote.repository.UserRepository;
import com.thevirtugroup.postitnote.service.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserConverter userConverter;

    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication auth
    )throws IOException {
        writeResponse(response);
    }

    private void writeResponse(HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSuccess(true);
        User loggedUser = fetchCurrentUser();
        UserResponseDto dto = userConverter.toDto(loggedUser);
        loginResponse.setUser(dto);
        objectMapper.writeValue(response.getWriter(), loginResponse);
    }

    private User fetchCurrentUser() {
        final SecurityUserWrapper loggedInUser = SecurityContext.getLoggedInUser();
        return fetchUser(loggedInUser.getId());
    }

    private User fetchUser(Long id){
        Objects.requireNonNull(id);
        return Optional.ofNullable(userRepo.findById(id)).orElseThrow(() -> new IllegalArgumentException("User id not found " + id));
    }
}