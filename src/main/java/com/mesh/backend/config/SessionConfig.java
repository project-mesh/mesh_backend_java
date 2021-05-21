package com.mesh.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableSpringHttpSession
public class SessionConfig {

    private final int MaxInactiveInternal = 30 * 60;

    @Bean
    public MapSessionRepository sessionRepository(){
        MapSessionRepository mapSessionRepository = new MapSessionRepository(new ConcurrentHashMap<>());
        mapSessionRepository.setDefaultMaxInactiveInterval(MaxInactiveInternal);
        return mapSessionRepository;
    }
}
