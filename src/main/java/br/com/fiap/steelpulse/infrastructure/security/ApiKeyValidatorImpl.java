package br.com.fiap.steelpulse.infrastructure.security;

import br.com.fiap.steelpulse.application.service.ApiKeyValidator;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ApiKeyValidatorImpl implements ApiKeyValidator {

    @ConfigProperty(name = "api.key")
    String validApiKey;

    @Override
    public boolean isValid(String apiKey) {
        return apiKey != null && validApiKey.equals(apiKey);
    }

    @Override
    public boolean isPresent(String apiKey) {
        return apiKey != null && !apiKey.trim().isEmpty();
    }
}
