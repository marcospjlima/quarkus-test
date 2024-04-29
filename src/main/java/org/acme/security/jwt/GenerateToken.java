package org.acme.security.jwt;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;

import org.acme.UsuarioEntity;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GenerateToken {
	
	@ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public String generateTokenJWT(UsuarioEntity user){
        String token =
                Jwt.issuer(issuer)
                        .expiresAt(Instant.now().plus(Duration.ofHours(1)))
                        .upn(user.getEmail())
                        .groups(new HashSet<>(Arrays.asList("User")))
                        .sign();
        System.out.println(token);
        return token;
    }
}