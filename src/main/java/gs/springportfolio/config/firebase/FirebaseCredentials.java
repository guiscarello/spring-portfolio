package gs.springportfolio.config.firebase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FirebaseCredentials {

    private String private_key_id;
    private String client_x509_cert_url;
    private String client_id;
    private String token_uri;
    private String auth_provider_x509_cert_url;
    private String client_email;
    private String private_key;
    private String project_id;
    private String auth_uri;
    private String type;

}

