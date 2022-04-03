package gs.springportfolio.api;

import gs.springportfolio.models.Social;
import gs.springportfolio.services.SocialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(path = "/api")
@RestController()
public class SocialController {

    @Autowired
    private SocialServiceImpl socialService;

    @GetMapping(path = "/social")
    public ResponseEntity<List<Social>> getSocialInfo(){
        return ResponseEntity.ok(this.socialService.getAllSocialInfo());
    }

}
