package gs.springportfolio.services.skills;

import gs.springportfolio.dto.SkillDTO;
import gs.springportfolio.models.Skill;
import gs.springportfolio.repos.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FirebaseSkillServiceImpl implements SkillService {

    @Autowired
    private final SkillRepo skillRepo;

    public FirebaseSkillServiceImpl(SkillRepo skillRepo) {
        this.skillRepo = skillRepo;
    }

    @Override
    public List<Skill> getAllSkills() {
        return this.skillRepo.findAll();
    }

    //Get Skills by Ids - This is used for projects where multiple skills were used
    @Override
    public Set<Skill> getSkillsByIds(List<Long> skillsIds) {
        return new HashSet<>(this.skillRepo.getSkillsByIds(skillsIds));
    }

    @Override
    public Skill addNewSkill(Skill skill) {

        return this.skillRepo.save(skill);
    }

    @Override
    public Skill editSkill(Long id, SkillDTO skillDTO) {
        Skill updateSkill = this.skillRepo
                .findById(id)
                .orElseThrow();
        if (skillDTO.getSkillLogoPath() != null){
            updateSkill.setSkillLogoPath(skillDTO.getSkillLogoPath());
        }
        updateSkill.setName(skillDTO.getName());
        updateSkill.setLevel(skillDTO.getLevel());
        updateSkill.setLevelPercentage(skillDTO.getLevelPercentage());
        updateSkill.setColor(skillDTO.getColor());
        return this.skillRepo.save(updateSkill);
    }

    @Override
    public Long deleteSkill(Long id) throws Exception {
        try{
            this.skillRepo.deleteById(id);
            return id;
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Something has gone wrong!", e);
        }
    }

}
