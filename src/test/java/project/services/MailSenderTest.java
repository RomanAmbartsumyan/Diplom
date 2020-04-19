package project.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
public class MailSenderTest {

    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private MailSender mailSender;

    @Test
    public void send() {
        String email = "r9854334307@mail.ru";
        String subject = "Hello";
        String message = "world";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("testpostforexperiment@yandex.ru");
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(email, subject, message);

        verify(javaMailSender, times(1)).send(mailMessage);
    }
}