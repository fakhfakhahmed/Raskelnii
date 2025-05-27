package tn.ecnam.resources.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.zxing.WriterException;
import tn.ecnam.resources.entity.Demande;
import tn.ecnam.resources.entity.Livreur;
import tn.ecnam.resources.entity.Product;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

public interface IDemandeService{
    Demande AddDemande(Demande demande);
    Demande AddDemandeToUser(Demande demande) throws Exception;
    void AccptedDemande(String demandeId) throws Exception;

    String updateKilogramsForDemande(String demandeId, double kilograms) throws IOException, WriterException;

    @Transactional
    Demande UpdateDemandeForLoggedInUser(Demande updatedDemande) throws Exception;

    void deleteDemandeById(String demandeId) throws Exception;

    Demande UpdateDemande(Demande demande);
    void deleteDemande(Demande demande);
    List<Demande> getAllDemande();
    Demande getDemande(String DemandeId);
}
