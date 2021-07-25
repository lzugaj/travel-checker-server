package com.luv2code.travelchecker.validation;

import com.luv2code.travelchecker.validation.validator.PasswordValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintValidatorContext;

@SpringBootTest
public class PasswordValidatorTest {

    @InjectMocks
    private PasswordValidator passwordValidator;

    @Mock
    private ConstraintValidatorContext validatorContext;

    @Test
    public void should_Return_True_When_Password_Is_Valid() {
        final String password = "#pAssword123";
        final boolean isPasswordValid = passwordValidator.isValid(password, validatorContext);

        Assertions.assertTrue(isPasswordValid);
    }

    @Test
    public void should_Return_False_When_Password_Is_Blank() {
        final String password = "";
        final boolean isPasswordValid = passwordValidator.isValid(password, validatorContext);

        Assertions.assertFalse(isPasswordValid);
    }

    @Test
    public void should_Return_True_When_Password_Not_Match_Pattern() {
        final String password = "#password";
        final boolean isPasswordValid = passwordValidator.isValid(password, validatorContext);

        Assertions.assertFalse(isPasswordValid);
    }
}
