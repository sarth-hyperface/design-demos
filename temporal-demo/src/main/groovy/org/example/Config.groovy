package org.example

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {
    @Bean(name = "taskQueue")
    String taskQueue() {
        "TQ-1286"
    }
}
