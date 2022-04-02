package gs.springportfolio.repos;

import gs.springportfolio.models.ProjectPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectPhotoRepo extends JpaRepository<ProjectPhoto, Long> {

}
