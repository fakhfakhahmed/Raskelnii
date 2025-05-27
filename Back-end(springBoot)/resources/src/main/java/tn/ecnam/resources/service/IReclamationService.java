package tn.ecnam.resources.service;

import tn.ecnam.resources.entity.Reclamation;

import java.util.List;

public interface IReclamationService {
    Reclamation AddReclmation(Reclamation reclamation);

    Reclamation AddReclamationToUser(Reclamation reclamation) throws Exception;

    void AddReclamationToLivreur(Reclamation reclamation) throws Exception;

    Reclamation UpdateReclamtion(Reclamation reclamation);
    void deleteReclmation(Reclamation reclamation);
    List<Reclamation> getAllReclamations();
    Reclamation getReclamation(String ReclamationId);
    Reclamation updateReclamationForUser( String reclamationId, Reclamation updatedReclamation) throws Exception;
    void deleteReclamation(String reclamationId) throws Exception;
    Reclamation updateReclamation(String reclamationId, Reclamation updatedReclamation)throws Exception;
}
