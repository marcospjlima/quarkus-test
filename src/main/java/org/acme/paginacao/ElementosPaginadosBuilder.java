package org.acme.paginacao;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import java.util.List;

public final class ElementosPaginadosBuilder {

	private ElementosPaginadosBuilder(){
		//Construtor oculto
	}

	public static final <T extends PanacheEntity> ElementosPaginados<T> build(PanacheQuery<T> query, Integer paginaAtual, Integer elementosPaginaAtual) {
		return new ElementosPaginados<>(query,paginaAtual,elementosPaginaAtual);
	}
	public static final <T> ElementosPaginados<T> build(List<T> listaPaginada, Integer paginaAtual, Long quantidadeTotal) {
		return new ElementosPaginados<>(listaPaginada, paginaAtual, quantidadeTotal);
	}
}
