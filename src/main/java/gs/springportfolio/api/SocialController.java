package gs.springportfolio.api;

import gs.springportfolio.models.Social;
import gs.springportfolio.services.socials.SocialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/api")
@RestController()
public class SocialController {

    @Autowired
    private SocialServiceImpl socialService;

    @GetMapping(path = "/socials")
    public ResponseEntity<List<Social>> getSocialInfo(){
        return ResponseEntity.ok(this.socialService.getAllSocialInfo());
    }

    @PostMapping(path = "/socials")
    public ResponseEntity<Social> getSocialInfo(
            @RequestParam("name") String name,
            @RequestParam("class") String bootstrapClass,
            @RequestParam("link") String link
    ){
        Social newSocial = new Social(
                name,
                bootstrapClass,
                link
        );
        return ResponseEntity.ok(this.socialService.createSocialInfo(newSocial));
    }

    @DeleteMapping(path = "/socials/{id}")
    public ResponseEntity<Long> deleteSocialInfo(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(this.socialService.deleteSocialInfo(id));
    }

}
