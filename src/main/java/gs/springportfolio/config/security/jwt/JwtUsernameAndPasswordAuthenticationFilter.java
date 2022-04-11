package gs.springportfolio.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import gs.springportfolio.config.security.classes.UsernameAndPasswordAuthenticationRequest;
import gs.springportfolio.config.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private JwtUtils jwtUtils;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.info("Request: {}", request.getInputStream().toString());
            UsernameAndPasswordAuthenticationRequest authReq = mapper.readValue(
                    request.getInputStream(),UsernameAndPasswordAuthenticationRequest.class);
            log.info("passworrd: {}", authReq.getPassword() );
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authReq.getUsername(),
                    authReq.getPassword()
            );
            log.info("Authentication: {}", authentication);
            return getAuthenticationManager().authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String token = jwtUtils.generateToken(auth);
        log.info("Token authentication length: {}", token.length());
        response.addHeader("token", jwtUtils.getTokenPrefix() + token);
        response.setHeader("authenticated", String.valueOf(auth.isAuthenticated()));

    }

}