package gs.springportfolio.services.socials;

import gs.springportfolio.models.Social;
import gs.springportfolio.repos.SocialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialServiceImpl implements SocialService{

    @Autowired
    private SocialRepo socialRepo;

    @Override
    public List<Social> getAllSocialInfo() {
        return this.socialRepo.findAll();
    }

}
