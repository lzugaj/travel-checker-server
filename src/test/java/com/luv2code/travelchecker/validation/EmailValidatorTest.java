package com.luv2code.travelchecker.validation;

import com.luv2code.travelchecker.validation.validator.EmailValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintValidatorContext;

@SpringBootTest
public class EmailValidatorTest {

    @InjectMocks
    private EmailValidator emailValidator;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void should_Return_True_When_Email_Is_Valid() {
        final String email = "spring.boot@gmail.com";
        final boolean isEmailValid = emailValidator.isValid(email, constraintValidatorContext);

        Assertions.assertTrue(isEmailValid);
    }

    @Test
    public void should_Return_False_When_Email_Is_Blank() {
        final String email = " ";
        final boolean isEmailValid = emailValidator.isValid(email, constraintValidatorContext);

        Assertions.assertFalse(isEmailValid);
    }

    @Test
    public void should_Return_False_When_Email_Not_Match_Pattern() {
        final String email = "spring.boot.com";
        final boolean isEmailValid = emailValidator.isValid(email, constraintValidatorContext);

        Assertions.assertFalse(isEmailValid);
    }
}
