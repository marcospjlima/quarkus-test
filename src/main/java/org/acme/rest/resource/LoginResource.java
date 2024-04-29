package org.acme.rest.resource;

import org.acme.rest.dto.LoginDTO;
import org.acme.rest.dto.LoginResponseDTO;
import org.acme.service.LoginService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

//@SuppressWarnings("deprecation")
//@Tag(name = "Login")
@Path("/login")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class LoginResource {

    @Inject
    LoginService loginService;

    @POST
    public LoginResponseDTO login(LoginDTO loginDTO) {

        return loginService.login(loginDTO);
    }
}
