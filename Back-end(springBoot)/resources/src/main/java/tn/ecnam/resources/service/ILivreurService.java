package tn.ecnam.resources.service;

import tn.ecnam.resources.entity.Livreur;
import tn.ecnam.resources.entity.User;

import java.util.List;

public interface ILivreurService {
    Livreur AddLivreur(Livreur livreur);
    Livreur findLivreurWithEmail(String email) throws Exception;
    public Livreur findLivreurWithUsername(String Username) throws Exception;
    Livreur LoggedInLivreur() throws Exception;
    Livreur UpdateLivreur(Livreur livreur);
    void deleteLivreur(Livreur user);
    List<Livreur> getAllLivreurs();
    Livreur getLivreurById(String LivreurId);
}
