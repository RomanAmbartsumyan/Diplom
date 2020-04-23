package project.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * Класс для отправки почты
 */
@Service
public class MailSender {
    private final JavaMailSender mailSender;

    public MailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Имя пользователя который отправляет письмо, данные находятся в application.properties
     */
    @Value("${spring.mail.username}")
    private String userName;

    /**
     * Отправка почты
     */
    @SneakyThrows
    public void send(String emailTo, String subject, String text){
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setFrom(userName);
        message.setTo(emailTo);
        message.setSubject(subject);
        message.setText(text, true);

        mailSender.send(mimeMessage);
    }
}
