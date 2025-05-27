package tn.ecnam.resources.entity.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ReclamationUpdateDto {
    private UserDto user;
    private String remake;
}
