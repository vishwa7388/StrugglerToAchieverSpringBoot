package struggler.to.achiever.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import struggler.to.achiever.constant.SecurityConstants;
import struggler.to.achiever.repository.UserRepository;
import struggler.to.achiever.security.AuthenticationFilter;
import struggler.to.achiever.security.AuthorizationFilter;
import struggler.to.achiever.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userDetailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;


    public SecurityConfig(UserService userDetailService, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.userDetailService = userDetailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        // Create Authentication Filter
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager);
        authenticationFilter.setFilterProcessesUrl("/create/token");

        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()  // Allow sign up
                .requestMatchers("/error").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/userservice/user/**").hasRole("ADMIN")
                .anyRequest().authenticated()  // All other requests require authentication
                .and()
                .authenticationManager(authenticationManager)
                .addFilter(authenticationFilter) // Add AuthenticationFilter first
                .addFilter(new AuthorizationFilter(authenticationManager,userRepository)) // Add AuthorizationFilter after AuthenticationFilter
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Stateless authentication (no sessions)

        return http.build();  // Return the configured SecurityFilterChain
    }



    // Password Encoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Hash passwords with BCrypt
    }
}