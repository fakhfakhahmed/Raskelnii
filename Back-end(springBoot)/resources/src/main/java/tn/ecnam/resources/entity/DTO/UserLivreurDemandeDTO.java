package tn.ecnam.resources.entity.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.ecnam.resources.entity.Demande;
import tn.ecnam.resources.entity.Livreur;
import tn.ecnam.resources.entity.User;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserLivreurDemandeDTO {
    private String userName;
    private String  phoneNumber;
    private String livreurName;
    private String demandeId;

}
