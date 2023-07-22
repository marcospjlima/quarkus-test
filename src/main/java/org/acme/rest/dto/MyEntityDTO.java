package org.acme.rest.dto;

import org.acme.MyEntity;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "id"}, allowGetters = true)
public class MyEntityDTO {
	
	@Schema(readOnly = true)
	private Long id;	
	
	
	private String field;
	
	public MyEntityDTO() {}
	
	public MyEntityDTO(MyEntity entity) {
		this.id = entity.id;
		this.field = entity.field;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	
	
	

}
