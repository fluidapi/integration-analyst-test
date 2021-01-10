package io.github.arkanjoms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class OpenApiConfig {

    @Value("${application.version}")
    private String projectVersion;

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Covid resume API")
                        .description("Covid resume service API")
                        .version(projectVersion)
                        .license(new License().name("MIT").url("https://mit-license.org"))
                        .contact(getContact()));
    }

    private Contact getContact() {
        Contact contact = new Contact();
        contact.setName("Rafael Ramos");
        contact.setUrl("http://github.com/arkanjoms");
        contact.setEmail("arkanjo.ms@gmail.com");
        return contact;
    }
}
