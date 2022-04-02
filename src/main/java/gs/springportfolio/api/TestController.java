package gs.springportfolio.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@RequestMapping("/api")
@RestController
@Slf4j
public class TestController {

    @Value("${application.test.photos.upload.folder}")
    private String testUploadFolder;

    @PostMapping(path = "/test")
    public void testFile(
            @RequestParam("file1") MultipartFile file
            ) throws IOException {
        log.info("File: {}", file);
        log.info("File name: {}", file.getOriginalFilename());
        Path path = Paths.get(testUploadFolder);
        String fileExtension = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
        log.info("File extension: {}", fileExtension);
        String randomFileName = UUID.randomUUID().toString();
        Path upload = path.resolve(randomFileName + "." + fileExtension);
        Files.copy(
                file.getInputStream(),
                upload,
                StandardCopyOption.REPLACE_EXISTING
        );
    }

}
