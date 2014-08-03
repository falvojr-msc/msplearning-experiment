package com.msplearning.repository.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.msplearning.entity.EducationalContent;
import com.msplearning.repository.EducationalContentRepository;
import com.msplearning.repository.jpa.generic.GenericRepositoryJpa;

/**
 * The EducationalContentRepositoryJpa class provides the persistence operations of entity {@link EducationalContent}.
 *
 * @author Venilton Falvo Junior (veniltonjr)
 */
@Repository("educationalContentRepository")
public class EducationalContentRepositoryJpa extends GenericRepositoryJpa<EducationalContent, Long> implements EducationalContentRepository {

	@SuppressWarnings("unchecked")
	@Override
	public List<EducationalContent> findByOwner(Long idOwner) {
		String jpql = "FROM EducationalContent WHERE idOwner = :idOwner";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idOwner", idOwner);
		return (List<EducationalContent>) this.findByJPQL(jpql, params);
	}

}
