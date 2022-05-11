package gs.springportfolio.services.contacts;

import gs.springportfolio.models.RecaptchaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Slf4j
@Service
public class ContactServiceImpl implements ContactService<RecaptchaResponse>{

    private final Environment environment;

    public ContactServiceImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public RecaptchaResponse validateReCaptcha(String token, WebClient client) {
        return client.post().uri(uriBuilder -> uriBuilder
                .queryParam("secret", this.environment.getRequiredProperty("google_recaptcha_secret_key"))
                .queryParam("response", token)
                .build()).retrieve().bodyToMono(RecaptchaResponse.class)
                .block();
    }

    public void sendEmail(String name, String email, String subject, String messageContent) throws MessagingException {
        Properties prop = new Properties();
        //prop.put("mail.debug", "true");
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        String emailUsername = this.environment.getRequiredProperty("GMAIL_USERNAME");
        String emailPassword = this.environment.getRequiredProperty("GMAIL_PASSWORD");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUsername, emailPassword);
            }
        });

        Message message = new MimeMessage(session);
        InternetAddress fromInternetAddress = new InternetAddress(email);
        try {
            fromInternetAddress.validate();
            message.setFrom(fromInternetAddress);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailUsername));
            message.setSubject(subject);

            String msg = name + " sent: <br>" + messageContent;
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException exception ){
            log.info("There was an error sending the message");
            exception.printStackTrace();
        }
    }

}
