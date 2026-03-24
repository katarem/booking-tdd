package io.github.katarem.infraestructure.config;

import io.github.katarem.domain.policy.TolerancePolicy;
import io.github.katarem.domain.policy.settings.TolerancePolicySettings;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ToleranceConfigurationProperties.class)
public class PolicyConfig {

    @Bean
    TolerancePolicy tolerancePolicy(ToleranceConfigurationProperties props) {
        var toleranceSettings = new TolerancePolicySettings(props.getQuantity(), props.getUnit());
        return new TolerancePolicy(toleranceSettings);
    }


}
