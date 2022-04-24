package gs.springportfolio.services.educations;

import gs.springportfolio.dto.EducationDTO;
import gs.springportfolio.models.Education;
import gs.springportfolio.repos.EducationRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirebaseEducationServiceImpl implements EducationService {

    private final EducationRepo educationRepo;

    private FirebaseEducationServiceImpl(EducationRepo educationRepo){
        this.educationRepo = educationRepo;
    }

    @Override
    public List<Education> getAllEducations() {

        return this.educationRepo.findAll();
    }

    @Override
    public Education addNewEducation(Education newEducation) {

        return this.educationRepo.save(newEducation);

    }

    @Override
    public Education editEducation(Long id, EducationDTO educationDTO) {
        Education updateEducation = this.educationRepo
                .findById(id)
                .orElseThrow();
        if (educationDTO.getInstitutionLogoPath() != null){
            updateEducation.setInstitutionLogoPath(educationDTO.getInstitutionLogoPath());
        }
        updateEducation.setName(educationDTO.getName());
        updateEducation.setInstitutionName(educationDTO.getInstitutionName());
        updateEducation.setType(educationDTO.getType());
        updateEducation.setYear(educationDTO.getYear());
        updateEducation.setDuration(educationDTO.getDuration());
        updateEducation.setHasTitle(educationDTO.isHasTitle());
        updateEducation.setTitle(educationDTO.getTitle());
        updateEducation.setDescription(educationDTO.getDescription());

        return this.educationRepo.save(updateEducation);
    }

    @Override
    public Long deleteEducation(Long id) throws Exception {
        try{
            this.educationRepo.deleteById(id);
            return id;
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Something has gone wrong!", e);
        }
    }

}
