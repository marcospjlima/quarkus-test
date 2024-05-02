package org.acme.service;

import java.util.Optional;

import org.acme.UsuarioEntity;
import org.acme.rest.dto.LoginDTO;
import org.acme.rest.dto.LoginResponseDTO;
import org.acme.security.jwt.GenerateToken;
import org.springframework.security.crypto.bcrypt.BCrypt;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;

@ApplicationScoped
public class LoginService {

	@Inject
    GenerateToken generateToken;

	@Inject
    TokenService tokenService;

    public LoginResponseDTO login(LoginDTO loginDTO) {

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

        Optional<UsuarioEntity> user = UsuarioEntity.findByEmail(loginDTO.getEmail());
        if (user.isEmpty()) {
            throw new NotAuthorizedException("Erro ao efetuar a autenticação");
        }

        if (!BCrypt.checkpw(loginDTO.getPassword(), user.get().getPassword())) {
            throw new NotAuthorizedException("Erro ao realizar o login, e-mail ou senha inválido!");
        }

        //String token = generateToken.generateTokenJWT(user.get());
        
        String token = tokenService.generate(loginDTO);

        loginResponseDTO.setEmail(user.get().getEmail());
        loginResponseDTO.setToken(token);

        return loginResponseDTO;
    }
}