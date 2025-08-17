package com.example.bank_rest.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Утилитарный класс для работы с JWT-токенами.
 */
@Component
public class JwtUtil {

    /**
     * Секретный ключ для подписи и проверки токенов.
     */
    @Value("${jwt_secret}")
    private String secret;
    /**
     * Срок действия токена в миллисекундах.
     */
    @Value("${jwt_expiration_period}")
    private int expirationPeriod; // 900000 milisecs = 15 mins

    /**
     * Генерирует новый JWT-токен для указанного пользователя.
     * <p>
     * Токен содержит имя пользователя, тему ("User Details") и информацию о создателе
     * ("BankRestApp"). Он подписывается с помощью секретного ключа и имеет ограниченный
     * срок действия.
     *
     * @param username Имя пользователя, для которого генерируется токен.
     * @return Сгенерированный JWT-токен в виде строки.
     * @throws IllegalArgumentException если предоставленный секретный ключ или алгоритм недействительны.
     * @throws JWTCreationException     если произошла ошибка при создании токена.
     */
    public String generateToken(String username) throws
            IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("BankRestApp")
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationPeriod))
                .sign(Algorithm.HMAC256(secret));

    }

    /**
     * Валидирует JWT-токен и извлекает имя пользователя из его полезной нагрузки.
     * <p>
     * Метод создает верификатор, который проверяет токен на основе секретного ключа,
     * темы и издателя. Если токен действителен, он извлекает имя пользователя
     * из поля "username" в полезной нагрузке.
     *
     * @param token JWT-токен для проверки.
     * @return Имя пользователя, содержащееся в токене.
     * @throws JWTVerificationException если токен недействителен (например, просрочен,
     * имеет неверную подпись или другие ошибки).
     */
    public String validateTokenAndRetrieveSubject(String token) throws
            JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("BankRestApp")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }
}