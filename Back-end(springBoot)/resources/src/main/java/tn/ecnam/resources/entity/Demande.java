package tn.ecnam.resources.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("Demande")

public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'Pending'")
    String etatDemande;
    Date date;
    String location;
    double kilograms;
    String etatLivrason;

    }





