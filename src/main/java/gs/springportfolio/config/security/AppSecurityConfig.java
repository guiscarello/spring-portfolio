package gs.springportfolio.config.security;


import gs.springportfolio.config.security.jwt.JwtAuthorizationFilter;
import gs.springportfolio.config.security.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import gs.springportfolio.config.security.user.AppUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter{

    private final PasswordEncoder passwordEncoder;
    private final AppUserDetailsService appUserDetailsService;

    public AppSecurityConfig(PasswordEncoder passwordEncoder, AppUserDetailsService appUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.appUserDetailsService = appUserDetailsService;
    }

    @Bean
    public JwtAuthorizationFilter appAuthorizationFilter(){
        return new JwtAuthorizationFilter();
    }

    @Bean
    public JwtUsernameAndPasswordAuthenticationFilter jwtUsernameAndPasswordAuthenticationFilter() throws Exception {
        return new JwtUsernameAndPasswordAuthenticationFilter(authenticationManagerBean());
    }

    @Bean
    public SimpleAuthorityMapper simpleAuthorityMapper(){
        SimpleAuthorityMapper mapper = new SimpleAuthorityMapper();
        mapper.setPrefix("ROLE_");
        mapper.setConvertToUpperCase(true);
        return mapper;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setAuthoritiesMapper(simpleAuthorityMapper());
        return provider;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/uploads/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.setAllowedOrigins(List.of("http://localhost:4200"));
                    cors.setAllowedMethods(List.of("GET", "PUT", "POST", "DELETE", "PATCH"));
                    cors.addAllowedHeader("*");
                    cors.addExposedHeader("token");
                    cors.addExposedHeader("authenticated");
                    return  cors;
                })
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtUsernameAndPasswordAuthenticationFilter())
                .addFilterAfter(appAuthorizationFilter(),
                        JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/**").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .and()
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .clearAuthentication(true)
                        .logoutSuccessHandler((request, response, auth) -> {
                            response.setStatus(HttpStatus.OK.value());
                        })
                );
    }

}
