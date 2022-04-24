package gs.springportfolio.services.contacts;

import org.springframework.web.reactive.function.client.WebClient;

public interface ContactService<t> {
    t validateReCaptcha(String token, WebClient client);
}
