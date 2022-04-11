package gs.springportfolio.config.security.jwt;

import gs.springportfolio.config.security.jwt.JwtUtils;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secretKey;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Request token: {}", request.getHeader("token"));
        String authToken = request.getHeader("token");

        if (jwtUtils.checkAuthToken(authToken)) {
            log.info("Token is empty or null or isn't a Bearer token");
            filterChain.doFilter(request, response);
            return;
        }
        String token = authToken.replace("Bearer ", "");
        log.info("Jwt token: {}", token);
        try {
            if(jwtUtils.validateToken(token)){
                String username = jwtUtils.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                log.info("authorities: {}", userDetails.getAuthorities());
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException e) {
            e.printStackTrace();
            response.setHeader("token", "");
            response.setHeader("authenticated", "false");
            SecurityContextHolder.getContext().setAuthentication(null);
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request,response);

    }

}
