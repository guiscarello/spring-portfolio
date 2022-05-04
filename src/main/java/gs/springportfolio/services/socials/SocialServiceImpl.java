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

    @Override
    public Social createSocialInfo(Social newSocial) {
        return this.socialRepo.save(newSocial);
    }

    @Override
    public Long deleteSocialInfo(Long id) throws Exception {
        try{
            this.socialRepo.deleteById(id);
            return id;
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Something has gone wrong!", e);
        }
    }
}
