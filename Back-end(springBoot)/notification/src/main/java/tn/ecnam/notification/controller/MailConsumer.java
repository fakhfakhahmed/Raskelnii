package tn.ecnam.notification.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Component
@Slf4j
public class MailConsumer {

    private static final String HOST = "smtp.mailtrap.io";
    private static final String PORT = "25";
    private static final String USERNAME = "184836e2d86b03";
    private static final String PASSWORD = "0dd87babe95622";
    private static final String FROM_EMAIL = "Recycle@gmail.com"; // Change to your Gmail address

    @RabbitListener(queues = "${queue.email-queue}")
    public void receiveMail(@Payload String fileBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(fileBody);



        String userName = node.get("user").get("username").asText();
        String email = node.get("user").get("email").asText();
        JsonNode remakeNode = node.get("remake");
        String remake = (remakeNode != null && !remakeNode.isNull()) ? remakeNode.asText() : "N/A";


        // Send the email using the received data
        try {
            sendEmail(email, "Reclamation Update", userName, email, remake);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(String toEmail, String subject, String name, String email, String remake) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        message.setSubject(subject);

        // Create the email content with HTML formatting
        String emailContent = "<html>"
                + "<body>"
                + "<h1>Hello " + name + ",</h1>"
                + "<p>Your reclamation has been updated:</p>"
                + "<p>Email: " + email + "</p>"
                + "<p>Remake: " + remake + "</p>"
                + "<p>Thank you for using our services.</p>"
                + "</body>"
                + "</html>";

        message.setContent(emailContent, "text/html");

        Transport.send(message);

        System.out.println("Email sent successfully to " + toEmail);
    }


    @RabbitListener(queues = "${queue.emailcode-queue}")
    public void receiveMailcode(@Payload String fileBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(fileBody);



        String userName = node.get("user").get("username").asText();
        String email = node.get("user").get("email").asText();
        String token = node.get("token").asText();
        JsonNode qrcodeNode = node.get("qrcode");
        String qrcode = (qrcodeNode != null && !qrcodeNode.isNull()) ? qrcodeNode.asText() : "N/A";



        // Send the email using the received data
        try {
            sendEmailcode(email, "Reclamation Update", userName, email, token,qrcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEmailcode(String toEmail, String subject, String name, String email, String token,String qrcodeNode) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        message.setSubject(subject);

        // Create the email content with HTML formatting
        String emailContent = "<html>"
                + "<body>"
                + "<h1>Hello " + name + ",</h1>"
                + "<p>Your redeem code information:</p>"
                + "<p>Username: " + email + "</p>"
                + "<p>Token: " + token + "</p>"
                + "<p>QR Code:</p>"
                + "<img src='data:image/png;base64," + qrcodeNode + "' alt='QR Code' />"
                + "<p>Thank you for using our services.</p>"
                + "</body>"
                + "</html>";

        message.setContent(emailContent, "text/html");

        Transport.send(message);

        System.out.println("Email sent successfully to " + toEmail);
    }
}
