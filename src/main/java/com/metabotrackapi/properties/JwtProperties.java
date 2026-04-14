package com.metabotrackapi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "metabotrack-api.jwt")
public class JwtProperties {

    /**
     * Token Name
     */
    private String tokenName;

    /**
     * Secret Key
     */
    private String secretKey;

    /**
     * Expire Time(ms)
     */
    private Long ttl;
}
