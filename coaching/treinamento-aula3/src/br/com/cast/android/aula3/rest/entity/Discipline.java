package br.com.cast.android.aula3.rest.entity;

import java.io.Serializable;

public class Discipline implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Campo que identifica o criador de uma {@link Discipline}.
	 * TODO: Cada participante deve colocar o seu respectivo identificador aqui.
	 */
	public static final long ID_OWNER = 21;

	private Long id;
	private String name;
	private String description;
	private Long idOwner = ID_OWNER;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getIdOwner() {
		return idOwner;
	}

}