package tn.ecnam.resources.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("Comment")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;
    @DBRef
    private User user;  // Reference to the user who made the comment
    private String text;    // The comment text
    private Date date;      // Timestamp for when the comment was made

}
