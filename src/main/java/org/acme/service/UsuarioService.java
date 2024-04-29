package org.acme.service;

import java.util.List;

import org.acme.MyEntity;
import org.acme.UsuarioEntity;
import org.acme.paginacao.ElementosPaginados;
import org.acme.paginacao.ElementosPaginadosBuilder;
import org.acme.service.exception.EntidadeNaoEncontradaException;

import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;

@ApplicationScoped
public class UsuarioService {

	public UsuarioEntity buscarUserPorIdOrThrow(String id) {
        return (UsuarioEntity) UsuarioEntity.findByIdOptional(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("User", "id", id));
    }
	
	
	public ElementosPaginados<UsuarioEntity> buscarTodos(@PathParam("pagina") Integer pagina, @PathParam("qtdPorPagina") Integer qtdPorPagina) {
        PanacheQuery<UsuarioEntity> buscarTodos = buscarTodos();
        Long qtdTotalMunicipios = buscarTodos.count();
        List<UsuarioEntity> municipios = buscarTodos.page(pagina, qtdPorPagina).list();
        
        return ElementosPaginadosBuilder.build(municipios, pagina, qtdTotalMunicipios);
    }
	
	@CacheResult(cacheName = "todos-entity")
    public PanacheQuery<UsuarioEntity> buscarTodos() {
        return MyEntity.findAll();
    }

	@Transactional
	public Boolean deleteMyEntityPorId(String id) {
        return UsuarioEntity.deleteById(id);
    }
	
	@Transactional
	public UsuarioEntity inserirUser(@Valid UsuarioEntity entity) {
        
		entity.persist();
		
		return entity;
    }
	
	
	public UsuarioEntity buscarPorEmail(String email) {
        return (UsuarioEntity) UsuarioEntity.buscarPorEmail(email)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("User", "email", email));
    }
	
	
	
}
