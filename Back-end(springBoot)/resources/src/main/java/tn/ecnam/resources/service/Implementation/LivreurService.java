package tn.ecnam.resources.service.Implementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.ecnam.resources.entity.Livreur;
import tn.ecnam.resources.repository.LivreurRepository;
import tn.ecnam.resources.service.ILivreurService;

import java.util.List;
import java.util.Optional;
@Service
public class LivreurService implements ILivreurService {
   @Autowired
    LivreurRepository lr;
    @Override
    public Livreur AddLivreur(Livreur livreur) {
        return lr.save(livreur);
    }

    @Override
    public Livreur findLivreurWithEmail(String email) throws Exception {
        if(lr.findByMail(email).isEmpty()){
            throw  new Exception( "there is no  user with  email : " +email) ;
        }
        return  lr.findByMail(email).get() ;    }

    @Override
    public Livreur findLivreurWithUsername(String Username) throws Exception {
        Optional<Livreur> optionalUser = lr.findByUserName(Username);
        if(optionalUser.isPresent()){
            return  optionalUser.get() ;
        }
        throw  new Exception( "there is no  Livreur with  email : " +Username) ;
    }

    @Override
    public Livreur LoggedInLivreur() throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return findLivreurWithUsername(username);
    }

    @Override
    public Livreur UpdateLivreur(Livreur livreur) {
       return lr.save(livreur);
    }

    @Override
    public void deleteLivreur(Livreur livreur) {
        lr.delete(livreur);

    }

    @Override
    public List<Livreur> getAllLivreurs() {
        return (List<Livreur>)  lr.findAll();
    }

    @Override
    public Livreur getLivreurById(String LivreurId) {
        return lr.findById(LivreurId).orElse(null);
    }
}
