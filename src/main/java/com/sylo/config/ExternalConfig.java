package com.sylo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author dhanavenkateshgopal on 7/6/20.
 * @project sylostats
 */
@Configuration
@ConfigurationProperties(prefix = "app.integration.external")
@Getter
@Setter
public class ExternalConfig {
    private Map<String, String> api;
}

