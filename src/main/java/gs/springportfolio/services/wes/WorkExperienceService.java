package gs.springportfolio.services.wes;

import gs.springportfolio.dto.WorkExperienceDTO;
import gs.springportfolio.models.WorkExperience;

import java.util.List;

public interface WorkExperienceService {

    List<WorkExperience> getAllWorkExperiences();
    WorkExperience addNewWorkExperience(WorkExperienceDTO newWorkExperienceDTO);
    WorkExperience editWorkExperience(Long id, WorkExperienceDTO workExperienceDTO);
    Long deleteWorkExperience(Long id) throws Exception;

}
