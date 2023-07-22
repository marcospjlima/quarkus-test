package org.acme.service.exception;

import org.apache.http.HttpStatus;

import jakarta.ws.rs.WebApplicationException;

public class EntidadeNaoEncontradaException extends WebApplicationException {
	private static final long serialVersionUID = 1L;
	
	private static final int STATUS_CODE = HttpStatus.SC_NOT_FOUND;

	
    public EntidadeNaoEncontradaException(final String nomeEntidade, final String nomeCampo, final String valorCampo) {
        super(String.format("Não foi possível encontrar %s com %s %s.", 
        		nomeEntidade, nomeCampo, valorCampo), STATUS_CODE);
    }
    

}
