package tn.ecnam.resources.service.Implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.ecnam.resources.config.QueueSender;
import tn.ecnam.resources.entity.DTO.ReedemCodeDto;
import tn.ecnam.resources.entity.DTO.UserDto;
import tn.ecnam.resources.entity.DTO.UserLivreurDemandeDTO;
import tn.ecnam.resources.entity.Demande;
import tn.ecnam.resources.entity.Livreur;
import tn.ecnam.resources.entity.Reedemcode;
import tn.ecnam.resources.entity.User;
import tn.ecnam.resources.repository.DemandeRepository;
import tn.ecnam.resources.repository.LivreurRepository;
import tn.ecnam.resources.repository.RedeemcodeRepository;
import tn.ecnam.resources.repository.UserRepository;
import tn.ecnam.resources.service.IDemandeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;


@Service
@Slf4j
public class DemandeService implements IDemandeService {

    @Autowired
    DemandeRepository dr;
    @Autowired
    UserService us;
    @Autowired
    UserRepository ur;
    @Autowired
    LivreurRepository lr;
    @Autowired
    LivreurService ls;
    @Autowired
    private QueueSender queueSender;
    @Autowired
    private RedeemcodeRepository redeemcodeRepository;
    @Override
    public Demande AddDemande(Demande demande) {
        return dr.save(demande);
    }

    @Override
    @Transactional
    public Demande AddDemandeToUser(Demande demande) throws Exception {
        User user = us.LoggedInUser();
        demande.setEtatDemande("Pending");
        demande.setDate(new Date());
        demande = dr.save(demande);
        Set<Demande> demandes = user.getDemandes();
        demandes.add(demande);
        user.setDemandes(demandes);
        ur.save(user);
        return demande;
    }

    @Override
    public void AccptedDemande(String demandeId)throws Exception {
        Livreur livreur =ls.LoggedInLivreur();
        Demande demande = dr.findById(demandeId).get();
        demande.setEtatDemande("Accepted");
        demande.setEtatLivrason("in progress");
        demande=dr.save(demande);


        Set<Demande> demandes = livreur.getDemandes();
        demandes.add(demande);
        livreur.setDemandes(demandes);
        lr.save(livreur);

        User user = ur.findUserByDemandeId(demandeId);
        if (user == null) {
            throw new Exception("User not found");
        }

        // Update etatDemande in User's demandes
        for (Demande userDemande : user.getDemandes()) {
            if (userDemande.getId().equals(demandeId)) {
                userDemande.setEtatDemande("Accepted");
                userDemande.setEtatLivrason("in progress");
            }
        }
        ur.save(user);

        // Create the DTO object
        UserLivreurDemandeDTO dto = new UserLivreurDemandeDTO();
        dto.setUserName(user.getUserName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setLivreurName(livreur.getUserName());
        dto.setDemandeId(demandeId);

        // Convert the DTO to JSON and send it
        ObjectMapper mapper = new ObjectMapper();
        String jsonMessage = mapper.writeValueAsString(dto);
        log.info(jsonMessage);
        queueSender.send(jsonMessage);

    }
    @Override
    public String updateKilogramsForDemande(String demandeId, double kilograms) throws IOException, WriterException {
        Demande demande = dr.findById(demandeId).orElse(null);
        if (demande != null) {
            demande.setKilograms(kilograms);
            demande.setEtatLivrason("Done");
            dr.save(demande);

            // Calculate points earned
            double pointsEarned = kilograms * 100; // 1 kg = 100 points

            // Generate a redeem code
            Reedemcode redeemCode = generateRedeemCode(pointsEarned);
            log.info(redeemCode.getToken());

            String qrCodeImageBase64 = generateQRCode(redeemCode.getToken());


            // Send an email to the user with the redeem code
            User user = ur.findUserByDemandeId(demandeId);
            if (user != null) {
            // Create the DTO User object
            UserDto userDto = new UserDto();
            userDto.setUsername(user.getUserName());
            userDto.setName(user.getFirstName() + user.getLastName());
            userDto.setEmail(user.getMail());

            // Create the DTO ReedemCode object
            ReedemCodeDto dto = new ReedemCodeDto();
            dto.setUser(userDto);
            dto.setToken(redeemCode.getToken());
            dto.setQrcode(qrCodeImageBase64);

                // Convert the DTO to JSON and send it
                ObjectMapper mapper = new ObjectMapper();
                String jsonMessage = mapper.writeValueAsString(dto);
                log.info(jsonMessage);
                queueSender.sendmailCode(jsonMessage);

            }
        }
        return null;
    }

    private Reedemcode  generateRedeemCode(double points) {
        // Implement a code generation logic (e.g., using UUID)
        Reedemcode redeemcode = new Reedemcode();
        redeemcode.setToken("REDEEM-" + points + "-" + UUID.randomUUID().toString());
        redeemcode.setValue(points);
        redeemcode.setCreatedTime(new Date()); // Set the timestamp when generating the code
        redeemcodeRepository.save(redeemcode); // Save the code to the database
        return redeemcode;
    }

    private String generateQRCode(String data) throws WriterException, IOException {
        int width = 200; // Adjust the width and height as needed
        int height = 200;

        // Create a QRCodeWriter
        Writer writer = new QRCodeWriter();

        // Encode the data into a BitMatrix
        BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height);

        // Create a ByteArrayOutputStream to write the image
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Write the BitMatrix to an image format (e.g., PNG)
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        // Convert the image to base64
        byte[] imageBytes = outputStream.toByteArray();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // Close the stream
        outputStream.close();

        return base64Image;
    }

    @Override
    @Transactional
    public Demande UpdateDemandeForLoggedInUser(Demande updatedDemande) throws Exception {
        User user = us.LoggedInUser();
        Demande existingDemande = dr.findById(updatedDemande.getId()).orElseThrow(() -> new Exception("Demande not found"));

        if (!user.getDemandes().contains(existingDemande)) {
            throw new Exception("You are not authorized to update this demande.");
        }
        existingDemande.setEtatDemande(updatedDemande.getEtatDemande());
        existingDemande.setDate(updatedDemande.getDate());
        existingDemande.setLocation(updatedDemande.getLocation());
        return dr.save(existingDemande);
    }

    @Override
    public void deleteDemandeById(String demandeId) throws Exception {
        User user = us.LoggedInUser();
        Demande demande = dr.findById(demandeId).orElseThrow(() -> new Exception("Demande not found"));

        if (!user.getDemandes().contains(demande)) {
            throw new Exception("You are not authorized to delete this demande.");
        }
        else {
            Set<Demande> userDemandes = user.getDemandes();
            userDemandes.remove(demande);
            user.setDemandes(userDemandes);
            ur.save(user);
        }

        dr.delete(demande);
    }


    @Override
    public Demande UpdateDemande(Demande demande) {
       return dr.save(demande);
    }

    @Override
    public void deleteDemande(Demande demande) {
        dr.delete(demande);
    }

    @Override
    public List<Demande> getAllDemande() {
        return (List<Demande>)  dr.findAll();
    }

    @Override
    public Demande getDemande(String DemandeId) {
        return dr.findById(DemandeId).orElse(null);
    }
}
