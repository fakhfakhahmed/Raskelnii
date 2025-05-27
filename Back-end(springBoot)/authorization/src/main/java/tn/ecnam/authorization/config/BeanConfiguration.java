package tn.ecnam.authorization.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ComponentScan
public class BeanConfiguration {

    private  final EmailConfig config ;

    @Autowired
    public BeanConfiguration(EmailConfig config) {
        this.config = config;
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    JavaMailSender mailSender(){

        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl() ;
        javaMailSender.setHost(config.getHost());
        javaMailSender.setPort(config.getPort());
        javaMailSender.setUsername(config.getUsername());
        javaMailSender.setPassword(config.getPassword());
        return  javaMailSender ;
    }
}
