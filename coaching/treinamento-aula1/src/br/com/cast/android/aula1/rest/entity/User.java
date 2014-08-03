package br.com.cast.android.aula1.rest.entity;

import java.io.Serializable;

import br.com.cast.android.aula1.rest.LoginRestClient;

/**
 * Classe que representa a entidade esperada no response do serviço REST {@link LoginRestClient#authenticate(User)}.
 *
 * @author venilton.junior
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String firstName;
	private String lastName;
	private Gender gender;
	private String email;
	private String password;
	private Long dateRegistration;
	private Long dateLastLogin;
	private Long idOwner;

	public User() {
		super();
	}

	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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

	public Long getDateRegistration() {
		return dateRegistration;
	}

	public Long getDateLastLogin() {
		return dateLastLogin;
	}

	public Long getIdOwner() {
		return idOwner;
	}
}
