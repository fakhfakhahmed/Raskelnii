package tn.ecnam.resources.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;
    @Autowired
    private Queue queueMail;

    @Autowired
    private Queue queueMailCode;


    public void send(String message) {
        rabbitTemplate.convertAndSend(this.queue.getName(), message);
    }

    public void sendmail(String message) {
        rabbitTemplate.convertAndSend(this.queueMail.getName(), message);
    }

    public void sendmailCode(String message) {
        rabbitTemplate.convertAndSend(this.queueMailCode.getName(), message);
    }
}