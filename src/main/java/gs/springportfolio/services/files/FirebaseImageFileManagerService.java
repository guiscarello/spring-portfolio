package gs.springportfolio.services.files;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import gs.springportfolio.config.firebase.FirebaseCredentials;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FirebaseImageFileManagerService implements FileManagerService<String, List<String>>{

    private final Environment environment;
    private final String projectId;
    private final String bucketName;

    public FirebaseImageFileManagerService(Environment environment) {
        this.environment = environment;
        this.projectId = environment.getRequiredProperty("FIREBASE_PROJECT_ID");
        this.bucketName = environment.getRequiredProperty("FIREBASE_BUCKET");
    }

    @Override
    public String uploadFile(MultipartFile file, String uploadPathFolder) {
        //TODO: Change file to unique identifier
        String fileName = uploadPathFolder + Objects.requireNonNull(file.getOriginalFilename());
        Storage storage = StorageOptions.newBuilder().setProjectId(this.projectId).build().getService();
        BlobId blobId = BlobId.of(this.bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .setCacheControl("public,max-age=31536000")
                .build();
        try {
            log.info(blobInfo.toString());
            storage.create(blobInfo, file.getBytes());
        } catch (IOException e) {
            log.error("Could not save the file" + e);
            e.printStackTrace();
        }
        log.info("Object " + fileName + " upload to bucket " + this.bucketName + " with contents " + file);
        log.info("Upload path: {}" , fileName );
        return fileName;
    }

    @Override
    public List<String> uploadMultipleFiles(List<MultipartFile> files, String uploadPathFolder) {
        //TODO: Change file to unique identifier
        List<String> filesNames = new ArrayList<>();
        for (MultipartFile file : files){
            if (!file.isEmpty()){
                String fileName = uploadPathFolder + Objects.requireNonNull(file.getOriginalFilename());
                Storage storage = StorageOptions.newBuilder().setProjectId(this.projectId).build().getService();
                BlobId blobId = BlobId.of(this.bucketName, fileName);
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                        .setContentType(file.getContentType())
                        .setCacheControl("public,max-age=31536000")
                        .build();
                try {
                    log.info(blobInfo.toString());
                    storage.create(blobInfo, file.getBytes());
                } catch (IOException e) {
                    log.error("Could not save the file" + e);
                    e.printStackTrace();
                }
                log.info("Object " + fileName + " upload to bucket " + this.bucketName + " with contents " + file);
                log.info("Upload path: {}" , fileName );
                filesNames.add(fileName);
            }
        }
        return filesNames;
    }

    @PostConstruct
    public void initializeFirebaseApp() throws IOException {
        //FileInputStream credentials = new FileInputStream(environment.getRequiredProperty("GOOGLE_APPLICATION_CREDENTIALS"));
        InputStream credentials = this.getFirebaseCredentials();
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(credentials))
                .setStorageBucket(this.projectId + ".appspot.com")
                .build();
        FirebaseApp.initializeApp(options);
    }

    private InputStream getFirebaseCredentials() throws JsonProcessingException {
        FirebaseCredentials firebaseCredentials = new FirebaseCredentials();

        firebaseCredentials.setType(environment.getRequiredProperty("FIREBASE_TYPE"));
        firebaseCredentials.setAuth_uri(environment.getRequiredProperty("FIREBASE_AUTH_URI"));
        firebaseCredentials.setProject_id(environment.getRequiredProperty("FIREBASE_PROJECT_ID"));
        firebaseCredentials.setClient_email(environment.getRequiredProperty("FIREBASE_CLIENT_EMAIL"));
        firebaseCredentials.setAuth_provider_x509_cert_url(environment.getRequiredProperty("FIREBASE_AUTH_PROVIDER_X509_CERT_URL"));
        firebaseCredentials.setToken_uri(environment.getRequiredProperty("FIREBASE_TOKEN_URI"));
        firebaseCredentials.setClient_id(environment.getRequiredProperty("FIREBASE_CLIENT_ID"));
        firebaseCredentials.setClient_x509_cert_url(environment.getRequiredProperty("FIREBASE_CLIENT_X509_CERT_URL"));
        firebaseCredentials.setPrivate_key_id(environment.getRequiredProperty("FIREBASE_PRIVATE_KEY_ID"));
        firebaseCredentials.setPrivate_key(environment.getRequiredProperty("FIREBASE_PRIVATE_KEY").replace("\\n", "\n"));

        ObjectMapper mapper = new ObjectMapper();
        String credentials = mapper.writeValueAsString(firebaseCredentials);
        return IOUtils.toInputStream(credentials, StandardCharsets.UTF_8);

    }
}

