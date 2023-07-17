package org.acme.rest.dto;

import org.acme.MyEntity;

public class MyEntityDTO {
	
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
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	
	
	

}
