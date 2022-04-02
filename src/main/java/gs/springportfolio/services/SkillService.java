package gs.springportfolio.services;

import gs.springportfolio.dto.SkillDTO;
import gs.springportfolio.models.Education;
import gs.springportfolio.models.Skill;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface SkillService {

    List<Skill> getAllSkills();
    Skill addNewSkill(Skill skill);
    Skill editSkill(Long id, SkillDTO skillDTO);
    Long deleteSkill(Long id) throws Exception;
    Set<Skill> getSkillsByIds(List<Long> skillsIds);

}
