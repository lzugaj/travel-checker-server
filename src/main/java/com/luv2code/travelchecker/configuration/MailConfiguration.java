package com.luv2code.travelchecker.configuration;

import com.luv2code.travelchecker.util.MailPropertiesUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class MailConfiguration {

    private String host;

    private Integer port;

    @Bean
    public JavaMailSender javaMailSender() {
        final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(getHost());
        javaMailSender.setPort(getPort());

        final Properties properties = new Properties();
        properties.setProperty(MailPropertiesUtil.MAIL_TRANSPORT_PROTOCOL, MailPropertiesUtil.SMPT);

        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
}
