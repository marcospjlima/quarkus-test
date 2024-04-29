package org.acme;

import java.util.Objects;
import java.util.Optional;

import org.hibernate.annotations.GenericGenerator;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

@Entity(name = "usuario")
public class UsuarioEntity extends PanacheEntityBase {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Size(min = 36, max = 36, message = "Campo id deve possuir no maximo 36 caracteres.")
    @Column(name = "id", length = 36, unique = true, updatable = false, nullable = false)
    public String id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
    private String email;
	
	@Column(name = "password")
    private String password;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEntity user = (UsuarioEntity) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    public static Optional<UsuarioEntity> buscarPorEmail(String email) {
        return email != null ? find("email", email).singleResultOptional() : Optional.empty();
    }
    
    public static Optional<UsuarioEntity> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}
