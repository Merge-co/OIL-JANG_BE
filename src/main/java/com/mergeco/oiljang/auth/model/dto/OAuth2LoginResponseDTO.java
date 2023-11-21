package com.mergeco.oiljang.auth.model.dto;

import com.mergeco.oiljang.auth.model.DetailsUser;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Data
@Builder
public class OAuth2LoginResponseDTO {
    private DetailsUser userDetails;
    private String accessToken;
}
