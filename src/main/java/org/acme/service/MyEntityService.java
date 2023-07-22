package org.acme.service;

import org.acme.MyEntity;
import org.acme.service.exception.EntidadeNaoEncontradaException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class MyEntityService {

	public MyEntity buscarMyEntityPorIdOrThrow(String id) {
        return (MyEntity) MyEntity.findByIdOptional(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("MyEntity", "id", id));
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
