package br.com.allanlarangeiras.socialnetwork;

import br.com.allanlarangeiras.socialnetwork.configuration.DefaultConfigurationProperties;
import br.com.allanlarangeiras.socialnetwork.configuration.SwaggerConfiguration;
import br.com.allanlarangeiras.socialnetwork.configuration.WebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebConfiguration.class, SwaggerConfiguration.class})
@EnableConfigurationProperties(DefaultConfigurationProperties.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
