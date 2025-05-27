package tn.ecnam.resources.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("Livreur")
public class Livreur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;
    String firstName;

    String userName;
    String lastName;
    String mail;
    String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Demande> demandes =new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Reclamation> reclamations=new HashSet<>() ;
}
