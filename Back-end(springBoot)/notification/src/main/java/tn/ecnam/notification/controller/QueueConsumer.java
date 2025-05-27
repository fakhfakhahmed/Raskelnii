package tn.ecnam.notification.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@Slf4j
public class QueueConsumer {
    @Value("${twilio.accountSid}")
    private String twilioAccountSid;

    @Value("${twilio.authToken}")
    private String twilioAuthToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;


    @RabbitListener(queues = "${queue.notification-queue}")
    public void receive(@Payload String fileBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(fileBody);

        JsonNode usernameNode = node.get("userName");
        String userName = (usernameNode != null && !usernameNode.isNull()) ? usernameNode.asText() : "N/A";
        JsonNode phoneNumberNode = node.get("phoneNumber");
        String phoneNumber = (phoneNumberNode != null && !phoneNumberNode.isNull()) ? phoneNumberNode.asText() : "N/A";
        JsonNode livreurNameNode = node.get("livreurName");
        String livreurName = (livreurNameNode != null && !livreurNameNode.isNull()) ? livreurNameNode.asText() : "N/A";
        JsonNode demandeIdNode = node.get("demandeId");
        String demandeId = (demandeIdNode != null && !demandeIdNode.isNull()) ? demandeIdNode.asText() : "N/A";


        // Use the data to send SMS using Twilio
        String smsMessage = "Accepted demande for User: " + userName +
                ", Livreur: " + livreurName +
                ", Demande ID: " + demandeId;

        String toPhoneNumber = "+216" + phoneNumber;
        // Send SMS using Twilio
        sendSms(toPhoneNumber, smsMessage);
    }


    private void sendSms(String toPhoneNumber, String message) {
        Twilio.init(twilioAccountSid, twilioAuthToken);

        Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                message
        ).create();
    }
}
