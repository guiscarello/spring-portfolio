package gs.springportfolio.services;

import gs.springportfolio.dto.WorkExperienceDTO;
import gs.springportfolio.models.Education;
import gs.springportfolio.models.WorkExperience;
import gs.springportfolio.repos.WorkExperienceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkExperienceServiceImpl implements WorkExperienceService {

    private final WorkExperienceRepo workExperienceRepo;
    @Value("${spring.server}")
    private String server;
    @Value("${spring.port}")
    private String port;

    @Autowired
    public WorkExperienceServiceImpl(WorkExperienceRepo workExperienceRepo) {
        this.workExperienceRepo = workExperienceRepo;
    }

    @Override
    public List<WorkExperience> getAllWorkExperiences(){
        List<WorkExperience> workExperiences = this.workExperienceRepo.findAll();
        for ( WorkExperience we : workExperiences){
            we.setCompanyLogoPath(this.createPathToFile(we));
        }
        return workExperiences;
    }

    @Override
    public WorkExperience addNewWorkExperience(WorkExperience newWorkExperience){
        WorkExperience workExperience = workExperienceRepo.save(newWorkExperience);

        workExperience.setCompanyLogoPath(
                this.createPathToFile(workExperience)
        );
        return workExperience;
    }

    @Override
    public WorkExperience editWorkExperience(Long id, WorkExperienceDTO workExperienceDTO){
        WorkExperience updateWorkExperience = workExperienceRepo
                .findById(id)
                .orElseThrow();

        updateWorkExperience.setCompanyName(workExperienceDTO.getCompanyName());
        if(workExperienceDTO.getCompanyLogoPath() != null){
            updateWorkExperience.setCompanyLogoPath(workExperienceDTO.getCompanyLogoPath());
        }
        updateWorkExperience.setDescription(workExperienceDTO.getDescription());
        updateWorkExperience.setStartDate(workExperienceDTO.getStartDate());
        updateWorkExperience.setEndDate(workExperienceDTO.getEndDate());
        updateWorkExperience.setPosition(workExperienceDTO.getPosition());
        updateWorkExperience.setTel(workExperienceDTO.getTel());
        updateWorkExperience.setCurrentWork(workExperienceDTO.isCurrentWork());

        final WorkExperience updatedWorkExperience = workExperienceRepo.save(updateWorkExperience);
        updatedWorkExperience.setCompanyLogoPath(
                this.createPathToFile(updatedWorkExperience)
        );
        return updatedWorkExperience;
    }

    @Override
    public Long deleteWorkExperience(Long id) throws Exception {
        try{
            workExperienceRepo.deleteById(id);
            return id;
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Something has gone wrong!", e);
        }
    }

    private String createPathToFile(WorkExperience workExperience){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(this.server)
                .append(":")
                .append(this.port)
                .append(workExperience.getCompanyLogoPath());
        return stringBuilder.toString();
    }

}
