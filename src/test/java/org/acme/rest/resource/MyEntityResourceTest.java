package org.acme.rest.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import jakarta.inject.Inject;

import org.acme.rest.PostgreSQLTestResource;
import org.acme.util.JsonBuilder;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(PostgreSQLTestResource.class)
@TestHTTPEndpoint(MyEntityResource.class)
class MyEntityResourceTest {
	
	private static final String ID_ESTADO_AL = "99100";
	private static final String ID_ESTADO_INEXISTENTE = "99";
	/*	
	private static final String SIGLA_ESTADO_RJ = "RJ";
	private static final String SIGLA_ESTADO_INEXISTENTE = "CC";
	
	private static final String URL_SIGLA = "siglaEstado/";
	private static final String URL_IBGE = "codigoIBGE/";
	private static final String URL_FILTRAR = "filtro";
	
	private static final String ESTADO_QUERY_PARAM_MOCK = "src/test/resources/mock/filtroMock.json";
	*/

	@Inject
	JsonBuilder jsonBuilder;

	/**
     * Teste para buscar os dados com sucesso, retornando os dados do estado.     
     */
	/*
	@Test
    void deveBuscarEstadoPorIdCorretamenteRetornandoEstado() {
		given()
			.get(ID_ESTADO_AL)
			.then().statusCode(HttpStatus.SC_OK)
			.body("size()", is(1))
			.body("[0].nome", is("Alagoas"))
			.body("[0].sigla", is("AL"))
			.body("[0].id", is("27"));
    }
	*/
	/**
     * Teste para buscar os dados do estado inexistente, retornando a mensagem de que não foi encontrado o estado.     
     */
	/*
	@Test
	void deveBuscarEstadosPorIdRetornandoError() {
		
		given()
			.get(URL_IBGE + ID_ESTADO_INEXISTENTE)
			.then().statusCode(HttpStatus.SC_OK)
			.body("size()", is(0));

	}
	*/
	/**
     * Teste para buscar os dados com sucesso, retornando os dados do estado.     
     */
	/*
	@Test
    void deveBuscarEstadoPorSiglaCorretamenteRetornandoEstado() {
		
		given()
			.get(URL_SIGLA + SIGLA_ESTADO_RJ)
			.then().statusCode(HttpStatus.SC_OK)
			.body("nome", is("Rio de Janeiro"),
				  "sigla", is("RJ"),
				  "id", is("33"));
		
    }
	*/
	/**
     * Teste para buscar os dados do pais inexistente, retornando a mensagem de que não foi encontrado o pais.     
     */
	
	@Test
	void deveBuscarEstadoPorSiglaRetornandoError() {
		
		given()
			.get(ID_ESTADO_INEXISTENTE)
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND)
			.body("error", containsString("Não foi possível encontrar MyEntity"));

	}
/*
	@Test
	void deveBuscarListaDeEstadosFiltrados() throws IOException {
		EstadoQueryParams estadoQueryParamsMock = new EstadoQueryParams();
		estadoQueryParamsMock.setFiltros(new EstadoFiltro());

		given().contentType(MediaType.APPLICATION_JSON)
				.body(jsonBuilder.toJson(estadoQueryParamsMock))
				.post(URL_FILTRAR)
				.then()
				.statusCode(HttpStatus.SC_OK);

		estadoQueryParamsMock.getFiltros().setId("1");
		estadoQueryParamsMock.getFiltros().setNome("A");
		estadoQueryParamsMock.getFiltros().setSigla("A");
		estadoQueryParamsMock.setOrdenarPor(EstadoOrdenarPor.ID);
		estadoQueryParamsMock.setTipoOrdenacao(SortOrder.ASCENDING);

		given().contentType(MediaType.APPLICATION_JSON)
				.body(jsonBuilder.toJson(estadoQueryParamsMock))
				.post(URL_FILTRAR)
				.then()
				.statusCode(HttpStatus.SC_OK);

		estadoQueryParamsMock.setOrdenarPor(EstadoOrdenarPor.NOME);
		estadoQueryParamsMock.setTipoOrdenacao(SortOrder.DESCENDING);

		given().contentType(MediaType.APPLICATION_JSON)
				.body(jsonBuilder.toJson(estadoQueryParamsMock))
				.post(URL_FILTRAR)
				.then()
				.statusCode(HttpStatus.SC_OK);

		estadoQueryParamsMock.setOrdenarPor(EstadoOrdenarPor.SIGLA);

		given().contentType(MediaType.APPLICATION_JSON)
				.body(jsonBuilder.toJson(estadoQueryParamsMock))
				.post(URL_FILTRAR)
				.then()
				.statusCode(HttpStatus.SC_OK);

		estadoQueryParamsMock.setFiltros(null);

		given().contentType(MediaType.APPLICATION_JSON)
				.body(jsonBuilder.toJson(estadoQueryParamsMock))
				.post(URL_FILTRAR)
				.then()
				.statusCode(HttpStatus.SC_BAD_REQUEST);


		given().contentType(MediaType.APPLICATION_JSON)
				.body(Files.readString(Paths.get(ESTADO_QUERY_PARAM_MOCK)))
				.post(URL_FILTRAR)
				.then()
				.statusCode(HttpStatus.SC_BAD_REQUEST)
				.body("error", is("Formato inválido: valor inválido para campo ordenarPor"));
	}
	*/
}
