package project.services;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
public class MailSenderTest {

    @Autowired
    private MailSender mailSender;

    @SneakyThrows
    @Test
    public void send() {
        String email = "r9854334307@mail.ru";
        String subject = "Hello";
        String sendMessage = "world";

        mailSender.send(email, subject, sendMessage);
    }
}