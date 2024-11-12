package struggler.to.achiever.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import struggler.to.achiever.constant.SecurityConstants;
import struggler.to.achiever.dto.UserDto;
import struggler.to.achiever.model.UserLoginRequestModel;
import struggler.to.achiever.service.UserService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    public AuthenticationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            UserLoginRequestModel creds = new ObjectMapper().readValue(request.getInputStream(),UserLoginRequestModel.class);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(),creds.getPassword(),new ArrayList<>()));
        }catch(IOException e){
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        byte[] secretKeyBytes = Base64.getEncoder().encode(SecurityConstants.TOKEN_SECRET.getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());
        Instant now= Instant.now();
        String username = ((User)authResult.getPrincipal()).getUsername();  // The authenticated username
        String token = generateToken(secretKey,username);

        UserService userService = (UserService) SpringApplicationContext.getBean("userService");
        UserDto userDto = userService.getUser(username);
        response.addHeader("Authorization", "Bearer " + token);
        if (userDto != null) {
            response.addHeader("UserId", userDto.getUserId().toString());
        } else {
            response.addHeader("UserId", "unknown");
        }
    }

    // Helper method to generate JWT
    private String generateToken(SecretKey secretKey,String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }
}
