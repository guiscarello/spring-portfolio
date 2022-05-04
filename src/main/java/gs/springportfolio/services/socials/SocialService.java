package gs.springportfolio.services.socials;

import gs.springportfolio.models.Social;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SocialService {

    List<Social> getAllSocialInfo();
    Social createSocialInfo(Social social);
    Long deleteSocialInfo(Long id) throws Exception;

}
