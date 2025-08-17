package com.example.bank_rest.service;

import com.example.bank_rest.dto.auth.RegisterRequestDto;
import com.example.bank_rest.dto.auth.RegisterResponseDto;
import com.example.bank_rest.entity.Role;
import com.example.bank_rest.entity.User;
import com.example.bank_rest.repository.RoleRepository;
import com.example.bank_rest.repository.UserRepository;
import com.example.bank_rest.service.impl.RegistrationServiceImpl;
import com.example.bank_rest.util.mapper.RegistrationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class RegistrationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RegistrationMapper registrationMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    private RegisterRequestDto registerRequestDto;
    private User user;
    private Role role;
    private RegisterResponseDto registerResponseDto;

    @BeforeEach
    void setUp() {
        registerRequestDto = new RegisterRequestDto(
                "Иван", "Иванов", "Иванович", "ivan.ivanov", "password123"
        );

        user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName("Иван");
        user.setLastName("Иванов");
        user.setUsername("ivan.ivanov");
        user.setPassword("password123");

        role = new Role();
        role.setId(UUID.randomUUID());
        role.setName("ROLE_CUSTOMER");

        registerResponseDto = new RegisterResponseDto("Иван", "Иванов", "ivan.ivanov");
    }

    @Test
    void registerUser_shouldMapUserSetRoleEncodePasswordAndReturnResponseDto() {
        when(registrationMapper.toEntity(registerRequestDto)).thenReturn(user);
        when(roleRepository.findRoleByName("ROLE_CUSTOMER")).thenReturn(Optional.ofNullable(role));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(registrationMapper.toRegisterResponseDto(user)).thenReturn(registerResponseDto);

        RegisterResponseDto result = registrationService.registerUser(registerRequestDto);

        assertNotNull(result);
        assertEquals("Иван", result.firstName());
        assertEquals("Иванов", result.lastName());
        assertEquals("ivan.ivanov", result.username());

        verify(registrationMapper).toEntity(registerRequestDto);
        verify(roleRepository).findRoleByName("ROLE_CUSTOMER");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(user);
        verify(registrationMapper).toRegisterResponseDto(user);

        assertEquals("encodedPassword", user.getPassword());
        assertEquals(List.of(role), user.getRoles());
    }
}
