package tn.ecnam.authorization.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RMQConfig {
    @Bean
    public Queue queue() {
        return new Queue("myQueue", true);
    }
}
