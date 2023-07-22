package org.acme.service.exceptionmapper;

import org.acme.service.exception.EntidadeNaoEncontradaException;
import org.acme.util.JsonBuilder;
import org.apache.http.HttpStatus;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EntidadeNaoEncontradaExceptionMapper implements ExceptionMapper<EntidadeNaoEncontradaException> {
	
	private static final Logger LOGGER = Logger.getLogger(EntidadeNaoEncontradaExceptionMapper.class);
    private static final int STATUS_CODE = HttpStatus.SC_NOT_FOUND;

    @Inject
    JsonBuilder builder;

    @Override
    public Response toResponse(EntidadeNaoEncontradaException exception) {
    	String errorMsg = exception.getMessage();
    	
        LOGGER.info(errorMsg);
        return Response.status(STATUS_CODE)
            .entity(builder.createErrorObject(errorMsg, STATUS_CODE))
            .build();
    }

}
