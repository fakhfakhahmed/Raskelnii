package tn.ecnam.resources.entity.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserDto {
    private String userId;
    private String username;
    private String name;
    private String email;
}
