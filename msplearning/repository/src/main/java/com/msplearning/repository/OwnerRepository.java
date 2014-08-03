package com.msplearning.repository;

import com.msplearning.entity.Owner;
import com.msplearning.repository.generic.GenericRepository;
import com.msplearning.repository.jpa.OwnerRepositoryJpa;

/**
 * Interface of {@link OwnerRepositoryJpa}.
 * 
 * @author Venilton Falvo Junior (veniltonjr)
 */
public interface OwnerRepository extends GenericRepository<Owner, Long> {

}
