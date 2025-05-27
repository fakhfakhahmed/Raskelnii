package tn.ecnam.resources.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.ecnam.resources.entity.Livreur;
import tn.ecnam.resources.service.ILivreurService;


import java.util.List;

@RestController
@RequestMapping("/Livreur")
public class LivreurController {
    @Autowired
    ILivreurService ls;


    @PostMapping("/add-livreur")
    public Livreur addLivreur(@RequestBody Livreur livreur){
        return ls.AddLivreur(livreur);
    }
    @GetMapping("/current-livreur")
    public Livreur getCurrentLivreur() throws Exception {
        return ls.LoggedInLivreur();
    }

    @GetMapping("/{livreurId}")
    public Livreur getLivreurById(@PathVariable String livreurId) {
        return ls.getLivreurById(livreurId);
    }

    @GetMapping("/all-livreurs")
    public List<Livreur> getAllLivreurs() {
        return ls.getAllLivreurs();
    }

    @PutMapping("/update-livreur")
    public Livreur updateLivreur(@RequestBody Livreur livreur) {
        return ls.UpdateLivreur(livreur);
    }

    @DeleteMapping("/delete-livreur/{livreurId}")
    public ResponseEntity<String> deleteLivreur(@PathVariable String livreurId) {
        Livreur livreurToDelete = ls.getLivreurById(livreurId);
        if (livreurToDelete != null) {
            ls.deleteLivreur(livreurToDelete);
            return ResponseEntity.ok("Livreur deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
}
