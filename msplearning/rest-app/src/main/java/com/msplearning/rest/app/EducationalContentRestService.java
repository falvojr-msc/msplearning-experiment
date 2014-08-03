package com.msplearning.rest.app;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msplearning.entity.EducationalContent;
import com.msplearning.rest.app.generic.GenericCrudRestService;
import com.msplearning.service.EducationalContentService;
import com.msplearning.service.generic.GenericCrudService;

/**
 * The EducationalContentRestService class provides the RESTful services of entity {@link EducationalContent}.
 *
 * @author Venilton Falvo Junior (veniltonjr)
 */
@Component
@Path("/educationalContent")
public class EducationalContentRestService extends GenericCrudRestService<EducationalContent, Long> {

	@Autowired
	private EducationalContentService educationalContentService;

	@Override
	protected GenericCrudService<EducationalContent, Long> getService() {
		return this.educationalContentService;
	}

	@GET
	@Path("/owner/{idOwner}")
	public List<EducationalContent> findByOwner(@PathParam("idOwner") Long idOwner) {
		return this.educationalContentService.findByOwner(idOwner);
	}
}
