package gs.springportfolio.services.files;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Slf4j
@Service
public class LocalImageFileManagerServiceImpl implements FileManagerService<String, List<String>>{

    public String uploadFile(MultipartFile companyLogo, String uploadFolder) {
        Path path = Paths.get( uploadFolder);
        //log.info("Upload path: {}", this.uploadFolder);
        Path uploadPath = path.resolve(Objects.requireNonNull(companyLogo.getOriginalFilename()));
        //log.info("Upload: {}", uploadPath.toString().replace("src/main/resources/static",""));
        try {
            /*
            * TODO: Create a table file that contains id to photo, upload file name, upload path and
            *  change file name to unique id
            */
            Files.copy(companyLogo.getInputStream(),
                    uploadPath,
                    StandardCopyOption.REPLACE_EXISTING
            );
            return uploadPath.toString().split("^.+(?=/uploads)")[1];
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public List<String> uploadMultipleFiles(List<MultipartFile> photos, String uploadFolder) {

        Path path = Paths.get( uploadFolder);
        List<String> uploadPaths = new ArrayList<>();
        for (MultipartFile photo : photos){
            if (!photo.isEmpty()){
                String fileExtension = Objects.requireNonNull(photo.getOriginalFilename()).split("\\.")[1];
                String randomFileName = UUID.randomUUID().toString();
                Path currentPhotoPath = path.resolve(randomFileName + "." + fileExtension);
                log.info(currentPhotoPath.toString());
                uploadPaths.add(currentPhotoPath.toString().split("^.+(?=/uploads)")[1]);
                try {
                    Files.copy(photo.getInputStream(),
                            currentPhotoPath,
                            StandardCopyOption.REPLACE_EXISTING
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
                }
            }

        }
        return uploadPaths;
    }


}
