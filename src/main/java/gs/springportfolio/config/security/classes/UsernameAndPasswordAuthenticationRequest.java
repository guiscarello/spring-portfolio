package gs.springportfolio.config.security.classes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsernameAndPasswordAuthenticationRequest {

    private String username;
    private String password;

    public UsernameAndPasswordAuthenticationRequest() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        //log.info(password);
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
