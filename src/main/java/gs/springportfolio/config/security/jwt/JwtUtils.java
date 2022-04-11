package gs.springportfolio.config.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Configuration
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.prefix}")
    private String tokenPrefix;

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public boolean checkAuthToken(String token){
        return ((token == null || token.isEmpty())  || !token.startsWith(tokenPrefix));
    }

    public String generateToken(Authentication authentication){
        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim("roles", authentication.getAuthorities())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 8 * 3600 * 1000))
            .signWith(Keys.hmacShaKeyFor(this.secretKey.getBytes()), SignatureAlgorithm.HS256)
            .compact();
    }

    public String getUsernameFromToken(String token){
        return this.getClaims(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try{
            this.getClaims(token);
            return true;
        } catch (MalformedJwtException e){
            e.printStackTrace();
            log.info("Malformed Jwt", e);
        } catch (ExpiredJwtException | UnsupportedJwtException e){
            e.printStackTrace();
            log.info("Expired or unsupported Jwt", e);
        } catch (ClaimJwtException e){
            e.printStackTrace();
            log.info("Error on expected claim Jwt", e);
        }
        return false;
    }

    private Jws<Claims> getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token);
    }

}
