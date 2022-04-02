package gs.springportfolio.services;

import gs.springportfolio.dto.EducationDTO;
import gs.springportfolio.models.Education;
import gs.springportfolio.models.WorkExperience;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EducationService {

    List<Education> getAllEducations();
    Education addNewEducation(Education education);
    Education editEducation(Long id, EducationDTO educationDTO);
    Long deleteEducation(Long id) throws Exception;

}
