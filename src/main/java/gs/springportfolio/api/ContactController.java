package gs.springportfolio.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(path = "/api")
@RestController
public class ContactController {


    @PostMapping(path = "/contacts")
    public void getContactInfo(
            @RequestParam(name = "token") String token

    ){
        log.info("token : {}", token);

    }

}
