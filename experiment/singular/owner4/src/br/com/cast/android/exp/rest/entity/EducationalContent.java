package br.com.cast.android.exp.rest.entity;

import java.io.Serializable;

public class EducationalContent implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Campo que identifica o criador de uma {@link EducationalContent}.
	 * TODO: Cada participante deve colocar o seu respectivo identificador aqui.
	 */
	public static final long ID_OWNER = 4;

	private Long id;
	private String title;
	private String url;
	private String footnote;
	private Long page;
	private Long idOwner = ID_OWNER;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFootnote() {
		return footnote;
	}

	public void setFootnote(String footnote) {
		this.footnote = footnote;
	}

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Long getIdOwner() {
		return idOwner;
	}

}