package tn.ecnam.resources.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import tn.ecnam.resources.entity.DTO.UserDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("Reclamation")
public class Reclamation {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    String id;
    String contenuRec;
    Date date;
    String etatClient;
    String etatRec;
    String remake; // Add this field
    String updatedBy; // Add this field
    @NotNull
    @Enumerated(EnumType.STRING)
    TypeReclamation typeRec;
    @Embedded
    private UserDto user;
}
