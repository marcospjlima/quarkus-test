package org.acme.paginacao;


import java.util.List;

public interface ConteudoPaginado<T> {

	/**
	 * @return Lista de elementos referentes a pagina atual
	 */
	public List<T> getElementosPaginados();

	/**
	 * @return Long indicando a quantidade total de elementos
	 */
	public Long getQuantidadeTotal();

	/**
	 *
	 * @return Long indicando a pagina atual
	 */
	public Integer getPaginaAtual();
}

