package com.example.bank_rest.service.impl;

import com.example.bank_rest.dto.auth.RegisterRequestDto;
import com.example.bank_rest.dto.auth.RegisterResponseDto;
import com.example.bank_rest.entity.Role;
import com.example.bank_rest.entity.User;
import com.example.bank_rest.repository.RoleRepository;
import com.example.bank_rest.repository.UserRepository;
import com.example.bank_rest.service.RegistrationService;
import com.example.bank_rest.util.mapper.RegistrationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RegistrationMapper registrationMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponseDto registerUser(RegisterRequestDto registerRequestDto) {
        log.info("Class: RegistrationServiceImpl, method: registerUser, registerRequestDto: {}", registerRequestDto);
        User user = registrationMapper.toEntity(registerRequestDto);
        log.info("Class: AdminServiceImpl, method: registerUser, user: {}", user);
        Role userRole = roleRepository.findRoleByName("ROLE_CUSTOMER");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(registerRequestDto.password()));

        userRepository.save(user);

        return registrationMapper.toRegisterResponseDto(user);
    }
}
