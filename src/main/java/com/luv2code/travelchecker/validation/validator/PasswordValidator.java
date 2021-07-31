package com.luv2code.travelchecker.validation.validator;

import com.luv2code.travelchecker.validation.Password;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    // Minimum eight characters, at least one letter, one number and one special character
    public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile(
            "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(password)) {
            return false;
        }

        final Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }
}
