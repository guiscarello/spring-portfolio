package gs.springportfolio.services;

import gs.springportfolio.models.Social;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SocialService {

    List<Social> getAllSocialInfo();

}
