package com.luv2code.travelchecker.util;

import com.luv2code.travelchecker.constants.SecurityConstants;
import com.luv2code.travelchecker.mock.JwtTokenMock;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUtilTest {

    @Test
    public void extractUsername() {
        // Token
        final String resetPasswordToken = JwtTokenMock.generateResetPasswordToken("john.doe@gmail.com", SecurityConstants.SECRET);

        try (MockedStatic<JwtUtil> mockedStatic = Mockito.mockStatic(JwtUtil.class)) {
            mockedStatic.when(() -> JwtUtil.extractUsername(resetPasswordToken, SecurityConstants.SECRET)).thenReturn("john.doe@gmail.com");
        }
    }
}
