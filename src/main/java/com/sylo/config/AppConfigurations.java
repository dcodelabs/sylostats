package com.sylo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author dhanavenkateshgopal on 15/5/20.
 * @project sylostats
 */
@Configuration
@ConfigurationProperties(prefix = "app.stock-market")
@Getter
@Setter
public class AppConfigurations {

    private List<String> markets;
}
