package com.sylo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dhanavenkateshgopal on 15/5/20.
 * @project sylostats
 */
@Configuration
@ConfigurationProperties(prefix = "config")
@Getter
@Setter
public class Stocks {
    private HashMap<String, List<String>> options;
}
