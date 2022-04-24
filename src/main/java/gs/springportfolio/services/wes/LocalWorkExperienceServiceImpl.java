package gs.springportfolio.services.wes;

import gs.springportfolio.dto.WorkExperienceDTO;
import gs.springportfolio.models.WorkExperience;
import gs.springportfolio.repos.WorkExperienceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalWorkExperienceServiceImpl implements WorkExperienceService {

    private final WorkExperienceRepo workExperienceRepo;
    @Value("${spring.server}")
    private String server;
    @Value("${spring.port}")
    private String port;

    @Autowired
    public LocalWorkExperienceServiceImpl(WorkExperienceRepo workExperienceRepo) {
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
    public WorkExperience addNewWorkExperience(WorkExperienceDTO newWorkExperienceDTO){
        WorkExperience newWorkExperience = new WorkExperience();
        newWorkExperience.setCompanyName(newWorkExperienceDTO.getCompanyName());

        if(newWorkExperienceDTO.getCompanyLogoPath() != null){
            newWorkExperience.setCompanyLogoPath(newWorkExperienceDTO.getCompanyLogoPath());
        }
        if(newWorkExperienceDTO.getEndDate() != null){
            newWorkExperience.setEndDate(newWorkExperienceDTO.getEndDate());
        }
        newWorkExperience.setDescription(newWorkExperienceDTO.getDescription());
        newWorkExperience.setStartDate(newWorkExperienceDTO.getStartDate());
        newWorkExperience.setPosition(newWorkExperienceDTO.getPosition());
        newWorkExperience.setTel(newWorkExperienceDTO.getTel());
        newWorkExperience.setCurrentWork(newWorkExperienceDTO.isCurrentWork());

        final WorkExperience addedWorkExperience = workExperienceRepo.save(newWorkExperience);
        addedWorkExperience.setCompanyLogoPath(
                this.createPathToFile(newWorkExperience)
        );

        return addedWorkExperience;
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
