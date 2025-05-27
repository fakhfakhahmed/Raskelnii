package tn.ecnam.resources.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tn.ecnam.resources.entity.Livreur;
import tn.ecnam.resources.entity.User;
import tn.ecnam.resources.service.ILivreurService;
import tn.ecnam.resources.service.IUserService;
@Component
@Slf4j
public class QueueConsumer {
    @Autowired
    IUserService us;
    @Autowired
    ILivreurService ls;

    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload String fileBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(fileBody);
        JsonNode rolesNode = node.get("roles");
        if (rolesNode != null && rolesNode.isArray()) {

            for (JsonNode roleNode : rolesNode) {
                // Assuming the role structure has a field named "roleName"
                String roleName = roleNode.get("roleName").asText();
                String email = node.get("email").asText();
                String username = node.get("userName").asText();
                String mobile = node.get("mobile").asText();
                if(roleName.equals("ROLE_USER")){
                    User user = new User();

                    user.setUserName(username);
                    user.setMail(email);
                    user.setPhoneNumber(mobile);
                    us.AddUser(user);
                }
                else if(roleName.equals("ROLE_LIVREUR")){
                    Livreur livreur = new Livreur();
                    livreur.setUserName(username);
                    livreur.setMail(email);
                    livreur.setPhoneNumber(mobile);
                    ls.AddLivreur(livreur);
                    log.info(livreur.toString());
                }
                log.info(roleName);
                // Process the roleName or other role-related data here
            }
        }

    }
}
