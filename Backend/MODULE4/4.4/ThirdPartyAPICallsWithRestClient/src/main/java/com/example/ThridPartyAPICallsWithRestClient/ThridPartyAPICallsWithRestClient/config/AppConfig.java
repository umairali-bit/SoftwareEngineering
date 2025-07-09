package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.config;

import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.auth.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditAwareImpl")
public class AppConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    AuditorAware<String> getAuditAwareImpl() {
        return new AuditorAwareImpl();

    }


}
