package com.msplearning.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The EducationalContent class.
 *
 * @author Venilton Falvo Junior (veniltonjr)
 */
@Entity
@Table(name = "tb_educational_content")
@SequenceGenerator(name = "sequenceEducationalContent", sequenceName = "sq_tb_educational_content")
public class EducationalContent implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "sequenceEducationalContent")
	@Column(name = "id")
	private Long id;

	@Column(name = "title", length = 50, nullable = false)
	private String title;

	@Column(name = "url", length = 500, nullable = false)
	private String url;

	@Column(name = "footnote", length = 50)
	private String footnote;

	@Column(name = "page", nullable = false)
	private Long page;

	@Column(name = "id_owner", nullable = false)
	private Long idOwner;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFootnote() {
		return this.footnote;
	}

	public void setFootnote(String footnote) {
		this.footnote = footnote;
	}

	public Long getPage() {
		return this.page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Long getIdOwner() {
		return this.idOwner;
	}

	public void setIdOwner(Long idOwner) {
		this.idOwner = idOwner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.id == null ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		EducationalContent other = (EducationalContent) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

}