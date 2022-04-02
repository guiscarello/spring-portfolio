package gs.springportfolio.repos;

import gs.springportfolio.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SkillRepo extends JpaRepository<Skill, Long> {

    @Query("select s from Skill s where id in :projectSkillsIds")
    List<Skill> getSkillsByIds(List<Long> projectSkillsIds);

}
