package org.acme.rest.resource;

import org.acme.MyEntity;
import org.acme.rest.dto.MyEntityDTO;
import org.acme.service.MyEntityService;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.hibernate.validator.constraints.NotBlank;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@SuppressWarnings("deprecation")
@Tag(name = "MyEntity")
@Path("/myentity")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MyEntityResource {
	
	@Inject
	MyEntityService myEntityService;

    @GET
    @Path("{id}")
    @Operation(summary = "Buscar MyEntity pelo ID.")
    @APIResponses(value = {
        @APIResponse(
            responseCode = "200",
            description = "MyEntity localizada e retornado com sucesso.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = MyEntityDTO.class))),
        @APIResponse(responseCode = "404", description = "MyEntity não encontrado"),
    })
    public Response buscarMyEntityPorId(
    		@Parameter(required = true, schema = @Schema(type = SchemaType.STRING, example = "1"))
            @NotBlank(message = "Parâmetro id não pode ser nulo ou vazio.")
            @Size(min = 1, max = 1, message = "ID deve possuir 1 caracter.")
            @PathParam("id")
            final String id) {
    	
        var myEntityDTO = new MyEntityDTO(myEntityService.buscarMyEntityPorIdOrThrow(id));
        return Response.ok(myEntityDTO).build();
    }
    
    
    @DELETE
    @Path("{id}")
    @Operation(summary = "Excluir MyEntity pelo ID.")
    @APIResponse(responseCode = "204", description = "Excluido com sucesso")    
    public void excluirMyEntityPorId(            
            @Parameter(required = true, schema = @Schema(type = SchemaType.STRING, example = "1"))
            @NotBlank(message = "Parâmetro id não pode ser nulo ou vazio.")
            @Size(min = 1, max = 1, message = "ID deve possuir 1 caracter.")
            @PathParam("id")
            final String id) {

    	myEntityService.deleteMyEntityPorId(id);    	
    }
    
    @POST
    @Operation(summary = "Salvar MyEntity ID.")
    @APIResponse(responseCode = "200", description = "Inserido com sucesso")
    public Response salvarMyEntityPorId(
    	@RequestBody(
            required = true,
            content = @Content(schema = @Schema(implementation = MyEntityDTO.class)))
        @NotNull(message = "DTO não pode ser nulo.")
        final MyEntityDTO myEntityDTO) {
    	
    	MyEntity entity = new MyEntity();
		entity.field = myEntityDTO.getField();
    	var dto = new MyEntityDTO( myEntityService.inserirMyEntity(entity));
    	
    	return Response.status(HttpStatus.SC_CREATED).entity(dto).build();
    }
}
