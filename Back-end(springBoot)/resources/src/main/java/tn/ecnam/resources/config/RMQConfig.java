package tn.ecnam.resources.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RMQConfig {
    @Bean
    public Queue queue() {
        return new Queue("NotificationQueue", true);
    }
    @Bean
    public Queue queueMail() {
        return new Queue("EmailQueue", true);
    }
    @Bean
    public Queue queueMailCode() {
        return new Queue("EmailCodeQueue", true);
    }
}
