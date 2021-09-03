package com.luv2code.travelchecker.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Data
@Configuration
@ConfigurationProperties(prefix = "luv2code.travel-checker.locale")
public class MessageSourceConfiguration {

    private String baseName;

    private String defaultLocale;

    private String defaultEncoding;

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(getBaseName());
        messageSource.setDefaultEncoding(getDefaultEncoding());
        messageSource.setDefaultLocale(new Locale(getDefaultLocale()));
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
