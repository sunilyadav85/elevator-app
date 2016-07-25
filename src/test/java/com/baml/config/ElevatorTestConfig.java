package com.baml.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;


@Configuration
@ActiveProfiles("test")
@Import(ElevatorConfig.class)
public class ElevatorTestConfig {
}
