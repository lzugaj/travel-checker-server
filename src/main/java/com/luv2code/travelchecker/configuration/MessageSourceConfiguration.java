package com.luv2code.travelchecker.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class MessageSourceConfiguration {

    private final LocaleConfiguration localeConfiguration;

    @Autowired
    public MessageSourceConfiguration(final LocaleConfiguration localeConfiguration) {
        this.localeConfiguration = localeConfiguration;
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(localeConfiguration.getBaseName());
        messageSource.setDefaultEncoding(localeConfiguration.getDefaultEncoding());
        messageSource.setDefaultLocale(new Locale(localeConfiguration.getDefaultLocale()));
        messageSource.setUseCodeAsDefaultMessage(false);
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        final LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
