package gs.springportfolio.services.wes;

import gs.springportfolio.dto.WorkExperienceDTO;
import gs.springportfolio.models.WorkExperience;
import gs.springportfolio.repos.WorkExperienceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirebaseWorkExperienceServiceImpl implements WorkExperienceService {

    private final WorkExperienceRepo workExperienceRepo;

    @Autowired
    public FirebaseWorkExperienceServiceImpl(WorkExperienceRepo workExperienceRepo) {
        this.workExperienceRepo = workExperienceRepo;
    }

    @Override
    public List<WorkExperience> getAllWorkExperiences(){
        return this.workExperienceRepo.findAll();
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

        return workExperienceRepo.save(newWorkExperience);
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

        return workExperienceRepo.save(updateWorkExperience);
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

}
