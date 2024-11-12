package struggler.to.achiever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@ComponentScan({"struggler.to.achiever.controller","struggler.to.achiever.service","struggler.to.achiever.util","struggler.to.achiever.configuration"})
@EntityScan("struggler.to.achiever.model")
@EnableJpaRepositories("struggler.to.achiever.repository")
public class AchieverApplication {

	public static void main(String[] args) {
		SpringApplication.run(AchieverApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
