package com.plathanus.sales.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "auth.service")
@Data
public class AuthServiceProperties {

    private String url;

}
