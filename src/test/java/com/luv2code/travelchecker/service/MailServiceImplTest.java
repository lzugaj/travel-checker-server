package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.configuration.MessageHelperConfiguration;
import com.luv2code.travelchecker.service.impl.MailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailServiceImplTest {

    @InjectMocks
    private MailServiceImpl mailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private MessageHelperConfiguration messageHelperConfiguration;

    @BeforeEach
    public void setup() {

    }
}