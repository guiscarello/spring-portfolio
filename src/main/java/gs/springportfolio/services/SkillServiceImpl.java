package gs.springportfolio.services;

import gs.springportfolio.dto.SkillDTO;
import gs.springportfolio.models.Skill;
import gs.springportfolio.repos.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SkillServiceImpl implements SkillService{

    @Autowired
    private final SkillRepo skillRepo;
    @Value("${spring.server}")
    private String server;
    @Value("${spring.port}")
    private String port;

    public SkillServiceImpl(SkillRepo skillRepo) {
        this.skillRepo = skillRepo;
    }

    @Override
    public List<Skill> getAllSkills() {
        List<Skill> skills = this.skillRepo.findAll();
        for (Skill s : skills){
            s.setSkillLogoPath(
                    this.createPathToFile(s)
            );
        }
        return skills;
    }

    //Get Skills by Ids - This is used for projects where multiple skills were used
    @Override
    public Set<Skill> getSkillsByIds(List<Long> skillsIds) {
        return new HashSet<>(this.skillRepo.getSkillsByIds(skillsIds));
    }

    @Override
    public Skill addNewSkill(Skill skill) {
        Skill newSkill = this.skillRepo.save(skill);
        newSkill.setSkillLogoPath(
                this.createPathToFile(newSkill)
        );
        return newSkill;
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
        final Skill updatedSkill = this.skillRepo.save(updateSkill);
        updatedSkill.setSkillLogoPath(
                this.createPathToFile(updatedSkill)
        );
        return updatedSkill;
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

    private String createPathToFile(Skill skill){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(this.server)
                .append(":")
                .append(this.port)
                .append(skill.getSkillLogoPath());
        return stringBuilder.toString();
    }

}
