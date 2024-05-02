package org.acme.rest.resource;

import org.acme.MyEntity;
import org.acme.paginacao.ElementosPaginados;
import org.acme.rest.dto.MyEntityDTO;
import org.acme.service.MyEntityService;
import org.acme.util.TokenUtils;
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

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
@Authenticated
public class MyEntityResource {
	
	@Inject
	MyEntityService myEntityService;

    @GET
    @PermitAll
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
            @Size(min = 1, max = 10, message = "ID deve possuir até 10 caracteres.")
            @PathParam("id")
            final String id) {
    	
        var myEntityDTO = new MyEntityDTO(myEntityService.buscarMyEntityPorIdOrThrow(id));
        return Response.ok(myEntityDTO).build();
    }
    
    
    @GET
    @PermitAll
    @Path("todosEntity/{pagina}/{qtdPorPagina}")
    @Operation(summary = "Busca e lista todos MyEntity com paginação")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Lista de MyEntity que foram localizados e retornado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ElementosPaginados.class))),
            @APIResponse(responseCode = "400", description = "Parâmentros de paginação inválidos", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @APIResponse(responseCode = "500", description = "Erro interno do sistema")
    })
    public ElementosPaginados<MyEntity> buscarTodos(
    		@PathParam("pagina") @Parameter(required = true, description = "Página consultada", example = "0")
            @PositiveOrZero(message = "Página não pode ser negativa") final Integer pagina,
            @PathParam("qtdPorPagina") @Parameter(required = true, description = "Número de registros por página", example = "10")
            @Positive(message = "Tamanho da página não pode ser 0 ou negativa") final Integer qtdPorPagina) {
        return myEntityService.buscarTodos(pagina, qtdPorPagina);
    }
    
    @DELETE
    @RolesAllowed({ TokenUtils.ROLE_USER })
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
    @RolesAllowed("User")
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
    /*
    @PUT
    @Path("{id}")
    @Operation(summary = "Atualizar MyEntity por ID.")
    @APIResponse(responseCode = "200", description = "Atualizado com sucesso")
    public Response atualizarsalvarMyEntityPorId(
    	@PathParam("id")
        final String id,
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
    */
}
