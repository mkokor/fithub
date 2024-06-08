package com.fithub.services.mealplan.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fithub.services.mealplan.core.context.UserContext;
import com.fithub.services.mealplan.dao.model.UserEntity;
import com.fithub.services.mealplan.dao.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserContextFilter extends HttpFilter {

    private static final long serialVersionUID = 1L;

    private final UserRepository userRepository;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String userUuid = request.getHeader("Authorization");

        if (userUuid != null) {
            UserContext userContext = UserContext.getCurrentContext();
            Optional<UserEntity> user = userRepository.findById(userUuid);
            user.ifPresent(userEntity -> userContext.setUser(userEntity));
        }

        try {
            chain.doFilter(request, response);
        } finally {
            UserContext.clear();
        }
    }

}