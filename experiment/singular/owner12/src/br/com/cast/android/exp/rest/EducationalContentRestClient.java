package br.com.cast.android.exp.rest;

import java.util.List;

import org.androidannotations.annotations.rest.Delete;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Put;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import br.com.cast.android.exp.rest.entity.EducationalContent;

/**
 * Interface que prove os serviços REST da entidade {@link EducationalContent}.
 * 
 * @author venilton.junior
 */
@Rest(rootUrl = "http://msplearning-veniltonjr.rhcloud.com/experiment/rest/", converters = { MappingJacksonHttpMessageConverter.class })
public interface EducationalContentRestClient extends RestClientSupport {

	@Post("/educationalContent")
	EducationalContent insert(EducationalContent educationalContent);

	@Get("/educationalContent/owner/{idOwner}")
	List<EducationalContent> findByOwner(Long idOwner);

	@Put("/educationalContent")
	EducationalContent update(EducationalContent educationalContent);

	@Delete("/educationalContent/{id}")
	void delete(Long id);
}
