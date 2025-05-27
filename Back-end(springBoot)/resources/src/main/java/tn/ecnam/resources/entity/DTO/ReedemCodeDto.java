package tn.ecnam.resources.entity.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ReedemCodeDto {
    private UserDto user;
    private String token;
    private String qrcode;

}
