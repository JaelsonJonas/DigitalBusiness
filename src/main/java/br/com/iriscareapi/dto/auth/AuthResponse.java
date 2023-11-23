package br.com.iriscareapi.dto.auth;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private final String tokenType = "Bearer";
    private String accessToken;
    private Long id;

}
