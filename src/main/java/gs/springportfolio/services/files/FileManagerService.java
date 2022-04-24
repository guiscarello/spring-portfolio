package gs.springportfolio.services.files;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface FileManagerService <T, U>{

    T uploadFile(MultipartFile file, String uploadPathFolder);
    U uploadMultipleFiles(List<MultipartFile> files, String uploadPathFolder);

}
