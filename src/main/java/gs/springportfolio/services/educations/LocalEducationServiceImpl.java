package gs.springportfolio.services.educations;

import gs.springportfolio.dto.EducationDTO;
import gs.springportfolio.models.Education;
import gs.springportfolio.repos.EducationRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalEducationServiceImpl implements EducationService {

    private final EducationRepo educationRepo;
    @Value("${spring.server}")
    private String server;
    @Value("${spring.port}")
    private String port;

    private LocalEducationServiceImpl(EducationRepo educationRepo){
        this.educationRepo = educationRepo;
    }

    @Override
    public List<Education> getAllEducations() {
        List<Education> educations = this.educationRepo.findAll();
        for(Education e : educations){
            e.setInstitutionLogoPath( this.createPathToFile(e));
        }
        return educations;
    }

    @Override
    public Education addNewEducation(Education newEducation) {

        Education education = this.educationRepo.save(newEducation);
        education.setInstitutionLogoPath(
                this.createPathToFile(education)
        );
        return education;

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

        Education education = this.educationRepo.save(updateEducation);
        education.setInstitutionLogoPath(
                this.createPathToFile(updateEducation)
        );
        return education;
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

    private String createPathToFile(Education education){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(this.server)
                .append(":")
                .append(this.port)
                .append(education.getInstitutionLogoPath());
        return stringBuilder.toString();
    }
}
