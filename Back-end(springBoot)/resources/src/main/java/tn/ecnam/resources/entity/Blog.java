package tn.ecnam.resources.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.stream.events.Comment;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("Blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;
    Date dateCreation;
    String contenu;
    @NotNull
    @Enumerated(EnumType.STRING)
    CategoryBlog TypeBlog ;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Comments> comments = new ArrayList<>();
    private Set<String> interestingUserIds = new HashSet<>();
    private Set<String> likesUserIds = new HashSet<>();
}



