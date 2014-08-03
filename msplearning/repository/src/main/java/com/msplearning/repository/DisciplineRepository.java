package com.msplearning.repository;

import java.util.List;

import com.msplearning.entity.Discipline;
import com.msplearning.repository.generic.GenericRepository;
import com.msplearning.repository.jpa.DisciplineRepositoryJpa;

/**
 * Interface of {@link DisciplineRepositoryJpa}.
 *
 * @author Venilton Falvo Junior (veniltonjr)
 */
public interface DisciplineRepository extends GenericRepository<Discipline, Long> {

	List<Discipline> findByOwner(Long idApp);

}
