package br.com.cast.android.aula2.rest.entity;

/**
 * Emun auxiliar que representa o gênero de um {@link User}.
 * 
 * @author venilton.junior
 */
public enum Gender {

	/**
	 * Male sex.
	 */
	M("M", "Male"),

	/**
	 * Female Sex.
	 */
	F("F", "Female");

	private String code;
	private String value;

	private Gender(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
}
