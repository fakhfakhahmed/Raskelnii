package tn.ecnam.resources.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import tn.ecnam.resources.entity.Demande;
import tn.ecnam.resources.service.IDemandeService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/Demande")
public class DemandeController {
    @Autowired
    IDemandeService ds;


    @PostMapping("/add-Demande")
    public Demande addDemande(@RequestBody Demande demande) throws Exception {
        return ds.AddDemandeToUser(demande);
    }
    @PutMapping("/update-Demande")
    public ResponseEntity<?> updateDemande(@RequestBody Demande updatedDemande) {
        try {
            Demande updated = ds.UpdateDemandeForLoggedInUser(updatedDemande);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to update the demande.");
        }
    }

    @PostMapping("/accept-Demande/{demandeId}")
    @PreAuthorize("hasRole('ROLE_LIVREUR')")
    public ResponseEntity<String> acceptDemande(@PathVariable String demandeId) {
        try {
            ds.AccptedDemande(demandeId);

            return ResponseEntity.ok("Demande accepted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{demandeId}/updateKilograms")
    public ResponseEntity<String> updateKilogramsForDemande(
            @PathVariable String demandeId,
            @RequestParam double kilograms
    ) {
        try {
            String result = ds.updateKilogramsForDemande(demandeId, kilograms);
            return ResponseEntity.ok(result);
        } catch (JsonProcessingException e) {
            // Handle JSON processing exception here, return an appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }



    @DeleteMapping("/delete-demande/{demandeId}")
    public ResponseEntity<String> deleteDemande(@PathVariable String demandeId) {
        try {
            ds.deleteDemandeById(demandeId);
            return ResponseEntity.ok("Demande deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Demande not found.");
        }
    }
    @GetMapping("/all-demandes")
    public List<Demande> getAllDemandes() {
        return ds.getAllDemande();
    }

    @GetMapping("/{demandeId}")
    public Demande getDemandeById(@PathVariable String demandeId) {
        return ds.getDemande(demandeId);
    }

}
