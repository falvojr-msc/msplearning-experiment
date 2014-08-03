package br.com.cast.android.aula2.rest;

import java.util.List;

import org.androidannotations.annotations.rest.Delete;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Put;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import br.com.cast.android.aula2.rest.entity.User;

/**
 * Interface que prove os serviços REST da entidade {@link User}.
 * 
 * @author venilton.junior
 */
@Rest(rootUrl = "http://10.11.21.235:8080/rest-app/rest/", converters = { MappingJacksonHttpMessageConverter.class })
public interface UserRestClient extends RestClientSupport {

	@Post("/user")
	User insert(User user);

	@Get("/user/owner/{idOwner}")
	List<User> findByOwner(Long idOwner);

	@Put("/user")
	User update(User user);

	@Delete("/user/{id}")
	void delete(Long id);
}
