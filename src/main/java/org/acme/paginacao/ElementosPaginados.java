package org.acme.paginacao;


import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

import java.util.List;

public class ElementosPaginados<T> implements ConteudoPaginado<T>{
	private List<T> elementos;
	private Long quantidadeTotalElementos;
	private Integer paginaAtual;

	public ElementosPaginados() {}
	
	public ElementosPaginados(PanacheQuery<T> query, Integer paginaAtual, Integer elementosPaginaAtual) {
		this.elementos = query.page(Page.of(paginaAtual, elementosPaginaAtual)).list();
		this.paginaAtual = paginaAtual;
		this.quantidadeTotalElementos = query.count();
	}

	public ElementosPaginados(List<T> listaPaginada, Integer paginaAtual, Long quantidadeTotal) {
		this.elementos = listaPaginada;
		this.paginaAtual = paginaAtual;
		this.quantidadeTotalElementos = quantidadeTotal;
	}

	@Override
	public List<T> getElementosPaginados() {
		return elementos;
	}
	
	public void setElementosPaginados(List<T> elementos) {
		this.elementos = elementos;
	}

	@Override
	public Long getQuantidadeTotal() {
		return quantidadeTotalElementos;
	}
	
	public void setQuantidadeTotal(Long quantidadeTotal) {
		this.quantidadeTotalElementos = quantidadeTotal;
	}

	@Override
	public Integer getPaginaAtual() {
		return paginaAtual;
	}
	
	public void setPaginaAtual(Integer paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

}

