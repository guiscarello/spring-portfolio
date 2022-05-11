package gs.springportfolio.services.contacts;

import org.springframework.web.reactive.function.client.WebClient;

import javax.mail.MessagingException;

public interface ContactService<t> {
    t validateReCaptcha(String token, WebClient client);
    void sendEmail(String name, String email, String subject, String messageContent) throws MessagingException;
}
