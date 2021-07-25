package com.luv2code.travelchecker.validation.validator;

import com.luv2code.travelchecker.validation.Password;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(password)) {
            return false;
        }

        // Minimum eight characters, at least one letter, one number and one special character
        final String passwordRegexPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        final Pattern pattern = Pattern.compile(passwordRegexPattern);
        return pattern.matcher(password).matches();
    }
}
