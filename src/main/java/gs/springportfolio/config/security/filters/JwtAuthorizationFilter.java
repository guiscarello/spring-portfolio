package gs.springportfolio.config.security.filters;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Authorization token: {}", request.getHeader("Authorization"));
        String authorizationHeader = request.getHeader("Authorization");
        boolean start = authorizationHeader.startsWith("Bearer ");
        log.info("Stars with Bearer: " + start);

        if (authorizationHeader.startsWith("Bearer ")) {
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            log.info("Token: {}", token);
            try {
                Jws<Claims> claims = Jwts
                        .parserBuilder()
                        .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                        .build()
                        .parseClaimsJws(token);
                Claims body = claims.getBody();
                String username = body.getSubject();
                var roles = (List<Map<String, String>>) body.get("role");
                log.info("username: {}", username);
                log.info("role: {}", body.get("role"));
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority(role.get("authority")))
                        .collect(Collectors.toList());
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        authorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request,response);
            } catch (JwtException e) {
                e.printStackTrace();
                throw new JwtException("The token can not be trusted", e);
            }
        }
        filterChain.doFilter(request,response);

    }

}
