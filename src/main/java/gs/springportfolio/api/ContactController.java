package gs.springportfolio.api;

import gs.springportfolio.models.RecaptchaResponse;
import gs.springportfolio.services.contacts.ContactServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import javax.mail.MessagingException;
import java.util.Arrays;


@Slf4j
@RequestMapping(path = "/api")
@RestController
public class ContactController {

    private final Environment environment;
    private final WebClient webClient;
    @Autowired
    private ContactServiceImpl contactService;

    public ContactController(Environment environment, WebClient.Builder webClientBuilder) {
        this.environment = environment;
        this.webClient = webClientBuilder.baseUrl("https://www.google.com/recaptcha/api/siteverify").build();
    }

    @PostMapping(path = "/contacts", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> sendEmail(
            @RequestParam(name = "token") String token,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "message") String message
    ) {
        RecaptchaResponse response = this.contactService.validateReCaptcha(token, this.webClient);
        if (response.isSuccess()){
            try {
                this.contactService.sendEmail(name, email, subject,message);
                return ResponseEntity.ok("El mensaje fue enviado con exito");
            } catch (MessagingException exception){
                exception.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        } else {
            return ResponseEntity.badRequest().body(
                    Arrays.stream(response.getErrorCodes())
                            .map(RecaptchaResponse.ErrorCode::getErrorDescription));
        }
    }

}
