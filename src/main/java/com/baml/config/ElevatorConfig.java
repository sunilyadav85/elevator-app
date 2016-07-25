package com.baml.config;

import com.baml.app.ElevatorApp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;

@Configuration
@ComponentScan(
        basePackages = {"com.baml"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {ElevatorApp.class})
)
@PropertySource(ignoreResourceNotFound = false, value = {"classpath:elevator-app.properties"})
public class ElevatorConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        propertySourcesPlaceholderConfigurer.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    public EmbeddedServletContainerFactory embeddedServletContainerFactory(@Value("${elevator.app.http.port}") int httpPort) {
        return new TomcatEmbeddedServletContainerFactory(httpPort);
    }
}
