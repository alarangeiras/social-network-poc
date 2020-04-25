package br.com.allanlarangeiras.socialnetwork.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("social-network-poc")
@Getter
@Setter
public class DefaultConfigurationProperties {

    private String salt;

}
