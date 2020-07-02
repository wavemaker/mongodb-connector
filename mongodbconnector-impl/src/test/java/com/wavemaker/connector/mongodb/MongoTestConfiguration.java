package com.wavemaker.connector.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.wavemaker.runtime.connector.configuration.ConnectorConfigurationBase;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 19/5/20
 */
@Configuration
@ComponentScan(basePackages = "com.wavemaker.connector")
public class MongoTestConfiguration extends ConnectorConfigurationBase {

    @Override
    public List<Resource> getClasspathPropertyResources() {
        return new ArrayList<>();
    }
}
