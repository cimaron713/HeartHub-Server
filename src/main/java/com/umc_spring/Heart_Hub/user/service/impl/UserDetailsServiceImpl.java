package com.umc_spring.Heart_Hub.user.service.impl;


import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        });

    }
}