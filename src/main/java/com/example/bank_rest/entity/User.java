package com.example.bank_rest.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сущность, представляющая пользователя в системе.
 * <p>
 * Этот класс сопоставляется с таблицей `users` в базе данных и реализует
 * интерфейс {@link UserDetails} для интеграции со Spring Security.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    /**
     * Уникальный идентификатор (UUID) пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Имя пользователя.
     */
    @Column(nullable = false)
    private String firstName;
    /**
     * Фамилия пользователя.
     */
    @Column(nullable = false)
    private String lastName;
    /**
     * Отчество пользователя.
     */
    @Column(nullable = false)
    private String patronymic;
    /**
     * Уникальное имя пользователя (логин), используемое для аутентификации.
     */
    @Column(unique = true, nullable = false)
    private String username;
    /**
     * Хешированный пароль пользователя.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Список ролей, назначенных пользователю.
     * <p>
     * Связь {@link ManyToMany} с таблицей `user_roles`.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;


    /**
     * Возвращает список полномочий (ролей) пользователя.
     * <p>
     * Требуется интерфейсом {@link UserDetails} для авторизации.
     *
     * @return Коллекция полномочий.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает пароль, используемый для аутентификации.
     *
     * @return Пароль пользователя.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Возвращает имя пользователя, используемое для аутентификации.
     *
     * @return Имя пользователя.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Возвращает {@code true}, если аккаунт пользователя не заблокирован.
     *
     * @return {@code true} (всегда), так как функционал блокировки аккаунта не реализован.
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * Возвращает полное имя пользователя (имя, отчество, фамилия).
     *
     * @return Полное имя в виде строки.
     */
    public String getFullName() {
        return firstName + " " + patronymic + " " + lastName;
    }
}