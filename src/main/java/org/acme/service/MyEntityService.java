package org.acme.service;

import java.util.List;

import org.acme.MyEntity;
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
public class MyEntityService {

	public MyEntity buscarMyEntityPorIdOrThrow(String id) {
        return (MyEntity) MyEntity.findByIdOptional(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("MyEntity", "id", id));
    }
	
	
	public ElementosPaginados<MyEntity> buscarTodos(@PathParam("pagina") Integer pagina, @PathParam("qtdPorPagina") Integer qtdPorPagina) {
        PanacheQuery<MyEntity> buscarTodos = buscarTodos();
        Long qtdTotalMunicipios = buscarTodos.count();
        List<MyEntity> municipios = buscarTodos.page(pagina, qtdPorPagina).list();
        
        return ElementosPaginadosBuilder.build(municipios, pagina, qtdTotalMunicipios);
    }
	
	@CacheResult(cacheName = "todos-entity")
    public PanacheQuery<MyEntity> buscarTodos() {
        return MyEntity.findAll();
    }

	@Transactional
	public Boolean deleteMyEntityPorId(String id) {
        return MyEntity.deleteById(id);
    }
	
	@Transactional
	public MyEntity inserirMyEntity(@Valid MyEntity entity) {
        
		entity.persist();
		
		return entity;
    }
	
	
	
}
