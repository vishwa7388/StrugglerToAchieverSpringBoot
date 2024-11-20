package struggler.to.achiever.configuration;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Tag(name = "Generate JWT Token", description = "Generate JWT Token and pass as a bearer token to authorize access.")
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("API Documentation for Struggler To Achiever")
                        .version("1.0")
                        .description("This is the API documentation for secured endpoints."))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));

        // Add token generation endpoint
        openAPI.path("/create/token", new PathItem()
                .post(new io.swagger.v3.oas.models.Operation()
                        .summary("Generate Token")
                        .description("This endpoint generates an authorization token.")
                        .requestBody(new RequestBody()
                                .description("Provide username and password")
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema()
                                                .addProperties("email", new Schema<String>().type("string"))
                                                .addProperties("password", new Schema<String>().type("string"))
                                        )))
                                .required(true))
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse().description("Token generated successfully"))
                                .addApiResponse("401", new ApiResponse().description("Invalid credentials"))
                                .addApiResponse("500", new ApiResponse().description("Server error")))));

        return openAPI;
    }
}

