package com.luv2code.travelchecker.validation.validator;

import com.luv2code.travelchecker.validation.Email;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public boolean isValid(final String email, final ConstraintValidatorContext context) {
        if (StringUtils.isBlank(email)) {
            return false;
        }

        final String emailRegexPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        final Pattern pattern = Pattern.compile(emailRegexPattern);
        return pattern.matcher(email).matches();
    }
}
