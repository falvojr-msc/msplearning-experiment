package br.com.cast.android.aula3.rest;

import java.util.List;

import org.androidannotations.annotations.rest.Delete;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Put;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import br.com.cast.android.aula3.rest.entity.Discipline;

/**
 * Interface que prove os serviços REST da entidade {@link Discipline}.
 * 
 * @author venilton.junior
 */
@Rest(rootUrl = "http://10.11.21.235:8080/rest-app/rest/", converters = { MappingJacksonHttpMessageConverter.class })
public interface DisciplineRestClient extends RestClientSupport {

	@Post("/discipline")
	Discipline insert(Discipline discipline);

	@Get("/discipline/owner/{idOwner}")
	List<Discipline> findByOwner(Long idOwner);

	@Put("/discipline")
	Discipline update(Discipline discipline);

	@Delete("/discipline/{id}")
	void delete(Long id);
}
