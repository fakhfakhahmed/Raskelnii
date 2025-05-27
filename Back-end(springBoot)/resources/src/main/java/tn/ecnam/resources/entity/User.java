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
@FieldDefaults (level = AccessLevel.PRIVATE)
@Document("User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;
    String firstName;
    String userName;
    String lastName;
    String mail;
    String phoneNumber;
    double PointsNumber;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Product> products=new HashSet<>()  ;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Blog> blogs=new HashSet<>()  ;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Reclamation> reclamations=new HashSet<>() ;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Demande> demandes =new HashSet<>();

    private Set<String> interestingEventIds = new HashSet<>();
    private Set<String> likedBlogsIds = new HashSet<>();

}
