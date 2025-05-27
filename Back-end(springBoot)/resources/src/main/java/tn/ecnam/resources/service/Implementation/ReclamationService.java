package tn.ecnam.resources.service.Implementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.ecnam.resources.config.QueueSender;
import tn.ecnam.resources.entity.DTO.ReclamationUpdateDto;
import tn.ecnam.resources.entity.DTO.UserDto;
import tn.ecnam.resources.entity.Livreur;
import tn.ecnam.resources.entity.Reclamation;
import tn.ecnam.resources.entity.User;
import tn.ecnam.resources.repository.LivreurRepository;
import tn.ecnam.resources.repository.ReclamationRepository;
import tn.ecnam.resources.repository.UserRepository;
import tn.ecnam.resources.service.ILivreurService;
import tn.ecnam.resources.service.IReclamationService;
import tn.ecnam.resources.service.IUserService;

import java.util.*;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;


@Service
@Slf4j
public class ReclamationService implements IReclamationService {
    @Autowired
    ReclamationRepository rr;
    @Autowired
    private IUserService us;
    @Autowired
    private ILivreurService ls;
    @Autowired
    private LivreurRepository lr;
    @Autowired
    private UserRepository ur;
    @Autowired
    private QueueSender queueSender;

    @Override
    public Reclamation AddReclmation(Reclamation reclamation) {
        return  rr.save(reclamation);
    }
    public String analyzeReclamationSentiment(String reclamationContent) {
        // Initialize Stanford NLP pipeline
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,parse,lemma,sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // Analyze sentiment
        CoreMap sentence = pipeline.process(reclamationContent).get(CoreAnnotations.SentencesAnnotation.class).get(0);
        String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
        log.info(sentiment);
        // Map sentiment to etatClient (you can customize this)
        if ("Positive".equals(sentiment)) {
            return "happy";
        } else if ("Negative".equals(sentiment)) {
            return "angry";
        } else {
            return "neutral";
        }
    }
    @Override
    public Reclamation AddReclamationToUser(Reclamation reclamation) throws Exception {
        User user = us.LoggedInUser();

        // Analyze sentiment and set etatClient
        String sentiment = analyzeReclamationSentiment(reclamation.getContenuRec());
        reclamation.setEtatClient(sentiment);
        reclamation.setEtatRec("Non traiter");
        // Set the reclamation date to the current date
        reclamation.setDate(new Date());
        Set<Reclamation> reclamations = user.getReclamations();
        reclamations.add(reclamation);
        user.setReclamations(reclamations);

        UserDto userDto = new UserDto();
        userDto.setUserId(user.getId());
        userDto.setUsername(user.getUserName());
        userDto.setName(user.getFirstName() + " " + user.getLastName());
        userDto.setEmail(user.getMail());
        reclamation.setUser(userDto);

        rr.save(reclamation);
        ur.save(user);
        return reclamation;
    }
    @Override
    public void deleteReclamation(String reclamationId) throws Exception {
        // Find the user
        User user = us.LoggedInUser();

        // Find the reclamation to delete
        Reclamation reclamationToDelete = user.getReclamations()
                .stream()
                .filter(r -> r.getId().equals(reclamationId))
                .findFirst()
                .orElseThrow(() -> new Exception("Reclamation not found"));

        // Remove the reclamation from the user's list
        user.getReclamations().remove(reclamationToDelete);

        // Delete the reclamation from the database
        rr.deleteById(reclamationId);

        // Save the user to update the changes
        ur.save(user);
    }

    @Override
    public Reclamation updateReclamationForUser( String reclamationId, Reclamation updatedReclamation) throws Exception {
        // Retrieve the user by their ID
        User user = us.LoggedInUser();
            // Find the Reclamation in the user's set
            Optional<Reclamation> optionalReclamation = user.getReclamations()
                    .stream()
                    .filter(r -> r.getId().equals(reclamationId))
                    .findFirst();

            if (optionalReclamation.isPresent()) {
                Reclamation existingReclamation = optionalReclamation.get();

                // Update the fields of the existing Reclamation
                String sentiment = analyzeReclamationSentiment(updatedReclamation.getContenuRec());
                existingReclamation.setContenuRec(updatedReclamation.getContenuRec());
                existingReclamation.setEtatClient(sentiment);
                existingReclamation.setTypeRec(updatedReclamation.getTypeRec());

                // Save the updated user (which will also update the Reclamation)
                ur.save(user);
                rr.save(existingReclamation);

                return existingReclamation;
            } else {
                throw new Exception("Reclamation not found with ID: " + reclamationId);
            }
    }

    // "traiter"
    @Override
    public Reclamation updateReclamation(String reclamationId, Reclamation updatedReclamation) throws Exception {
        // Fetch the existing reclamation by ID
        Reclamation existingReclamation = rr.findById(reclamationId).orElseThrow(() -> new Exception("Reclamation not found"));
        // Check if the reclamation is already "traiter"
        if ("traiter".equals(existingReclamation.getEtatRec())) {
            throw new Exception("Reclamation is already marked as 'traiter'");
        }

        // Get the currently logged-in admin user
        User adminUser = us.LoggedInUser();

        // Set the fields of the existing reclamation based on the updatedReclamation
        existingReclamation.setRemake(updatedReclamation.getRemake());
        existingReclamation.setUpdatedBy(adminUser.getUserName());
        existingReclamation.setEtatRec("traiter");


        // Save the updated reclamation
        existingReclamation= rr.save(existingReclamation);

        ReclamationUpdateDto reclamationUpdateDto = new ReclamationUpdateDto();
        UserDto userDto = new UserDto();
        userDto.setUsername(existingReclamation.getUser().getUsername());
        userDto.setName(existingReclamation.getUser().getName());
        userDto.setEmail(existingReclamation.getUser().getEmail());

        reclamationUpdateDto.setUser(userDto);

        reclamationUpdateDto.setRemake(existingReclamation.getRemake());
        // Convert the DTO to JSON and send it
        ObjectMapper mapper = new ObjectMapper();
        String jsonMessage = mapper.writeValueAsString(reclamationUpdateDto);
        log.info(jsonMessage);
        queueSender.sendmail(jsonMessage);

        return existingReclamation;
    }


    @Override
    public void AddReclamationToLivreur(Reclamation reclamation) throws Exception {
        Livreur livreur = ls.LoggedInLivreur();
        Set<Reclamation> reclamations = livreur.getReclamations();
        reclamations.add(reclamation);
        livreur.setReclamations(reclamations);
        lr.save(livreur);
    }

    @Override
    public Reclamation UpdateReclamtion(Reclamation reclamation) {
        return rr.save(reclamation);
    }

    @Override
    public void deleteReclmation(Reclamation reclamation) {
        rr.delete(reclamation);

    }

    @Override
    public List<Reclamation> getAllReclamations() {
        return (List<Reclamation>)  rr.findAll();
    }

    @Override
    public Reclamation getReclamation(String ReclamationId) {
         return rr.findById(ReclamationId).orElse(null);
    }


}
