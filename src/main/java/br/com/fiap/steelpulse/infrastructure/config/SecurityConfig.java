package br.com.fiap.steelpulse.infrastructure.config;

import br.com.fiap.steelpulse.application.service.ApiKeyValidator;
import br.com.fiap.steelpulse.infrastructure.security.ApiKeyValidatorImpl;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SecurityConfig {

    @ApplicationScoped
    public ApiKeyValidator apiKeyValidator(@ConfigProperty(name = "api.key") String validApiKey) {
        return new ApiKeyValidatorImpl(validApiKey);
    }

}
