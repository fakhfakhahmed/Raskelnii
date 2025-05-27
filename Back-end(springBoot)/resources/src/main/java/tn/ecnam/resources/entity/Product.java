package tn.ecnam.resources.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults (level = AccessLevel.PRIVATE)
@Document("Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;
    String title;
    String description;
    double Price;
     @NotNull
     @Enumerated(EnumType.STRING)
     CategoryProduct Categorie ;
    int nbrStock;
}
