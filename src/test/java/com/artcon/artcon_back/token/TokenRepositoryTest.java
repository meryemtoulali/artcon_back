package com.artcon.artcon_back.token;

import com.artcon.artcon_back.model.Role;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TokenRepositoryTest {
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindAllValidTokenByUser() {
        User user = userRepository.save(User.builder()
                .role(Role.USER)
                .firstname("John")
                .lastname("Doe")
                .username("username")
                .email("john@example.com")
                .password_hash("password")
                .type("Employee")
                .title("Developer")
                .gender("Male")
                .phone_number("123456789")
                .followers_count(0)
                .following_count(0)
                .build());

        Token validToken1 = tokenRepository.save(new Token(null, "token1", TokenType.BEARER , true, false, user));
        Token validToken2 = tokenRepository.save(new Token(null, "token2", TokenType.BEARER, true, false, user));
        Token expiredToken = tokenRepository.save(new Token(null, "expiredToken", TokenType.BEARER, true, true, user));
        Token revokedToken = tokenRepository.save(new Token(null, "revokedToken", TokenType.BEARER, true, true, user));

        List<Token> validTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        assertEquals(2, validTokens.size());
        assertTrue(validTokens.containsAll(Arrays.asList(validToken1, validToken2)));
    }

    @Test
    void testFindByToken() {
        Token token = tokenRepository.save(new Token(null,"testToken",TokenType.BEARER, true, false,null));

        Optional<Token> fetchedToken = tokenRepository.findByToken(token.getToken());

        assertTrue(fetchedToken.isPresent());
        assertEquals(token, fetchedToken.get());
    }

}