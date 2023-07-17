package org.acme.rest.resource;

import org.acme.MyEntity;
import org.acme.rest.dto.MyEntityDTO;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.hibernate.validator.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;






@Tag(name = "MyEntity")
@Path("/myentity")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MyEntityResource {

    //@Inject
    //PessoaService pessoaService;

    @GET
    @Path("{id}")
    @Operation(summary = "Buscar MyEntity pelo ID.")
    @APIResponses(value = {
        @APIResponse(
            responseCode = "200",
            description = "Pessoa localizada e retornado com sucesso.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = MyEntityDTO.class)))
    })
    public Response buscarMyEntityPorId(            
            @Parameter(required = true, schema = @Schema(type = SchemaType.STRING, example = "1"))
            @NotBlank(message = "Parâmetro id não pode ser nulo ou vazio.")
            @Size(min = 1, max = 1, message = "ID deve possuir 1 caracter.")
            @PathParam("id")
            final String id) {

    	
        var myEntityDTO = new MyEntityDTO(MyEntity.findById(id));
        return Response.ok(myEntityDTO).build();
    }
}
