package org.acme.rest.resource;

import java.util.Optional;

import org.acme.MyEntity;
import org.acme.UsuarioEntity;
import org.acme.rest.dto.MyEntityDTO;
import org.acme.rest.dto.UserDTO;
import org.acme.service.UsuarioService;
import org.acme.service.exception.BusinessException;
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
import org.springframework.security.crypto.bcrypt.BCrypt;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@SuppressWarnings("deprecation")
@Tag(name = "Usuario")
@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {
	
	@Inject
	UsuarioService userService;

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
    public Response buscarUserPorId(
    		@Parameter(required = true, schema = @Schema(type = SchemaType.STRING, example = "1"))
            @NotBlank(message = "Parâmetro id não pode ser nulo ou vazio.")
            @Size(min = 36, max = 36, message = "ID deve possuir 36 caracteres.")
            @PathParam("id")
            final String id) {
    	
        var myEntityDTO = new UserDTO(userService.buscarUserPorIdOrThrow(id));
        return Response.ok(myEntityDTO).build();
    }
    
    /*
    @GET
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
        return userService.buscarTodos(pagina, qtdPorPagina);
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
    */
    @POST
    @Operation(summary = "Salvar MyEntity ID.")
    @APIResponse(responseCode = "200", description = "Inserido com sucesso")
    public Response salvarUserPorId(
    	@RequestBody(
            required = true,
            content = @Content(schema = @Schema(implementation = UserDTO.class)))
        @NotNull(message = "DTO não pode ser nulo.")
        final UserDTO userDTO) {
    	
    	
    	
    	Optional<UsuarioEntity> userOptional = UsuarioEntity.findByEmail(userDTO.getEmail());

        if (userOptional.isPresent()) {
            throw new BusinessException("Usuário já cadastrado");
        }

        UsuarioEntity user = new UsuarioEntity();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()));

        
		
    	
    	//MyEntity entity = new MyEntity();
		//entity.field = myEntityDTO.getField();
    	var dto = new UserDTO( userService.inserirUser(user));
    	
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
