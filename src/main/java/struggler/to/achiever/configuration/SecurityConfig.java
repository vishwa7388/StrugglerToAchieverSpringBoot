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
import struggler.to.achiever.security.AuthorizationFilter;
import struggler.to.achiever.service.UserService;
import struggler.to.achiever.security.AuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private final UserService userDetailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(UserService userDetailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailService = userDetailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
                .anyRequest().authenticated()  // All other requests require authentication
                .and()
                .authenticationManager(authenticationManager)
                .addFilter(authenticationFilter) // Add AuthenticationFilter first
                .addFilter(new AuthorizationFilter(authenticationManager)) // Add AuthorizationFilter after AuthenticationFilter and before UsernamePasswordAuthenticationFilter
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // No sessions

        return http.build();
    }

    // Password Encoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Hash passwords with BCrypt
    }
}