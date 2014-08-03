package com.msplearning.repository.jpa;

import org.springframework.stereotype.Repository;

import com.msplearning.entity.Owner;
import com.msplearning.entity.User;
import com.msplearning.repository.OwnerRepository;
import com.msplearning.repository.jpa.generic.GenericRepositoryJpa;

/**
 * The UserRepositoryJpa class provides the persistence operations of entity
 * {@link User}.
 * 
 * @author Venilton Falvo Junior (veniltonjr)
 */
@Repository("ownerRepository")
public class OwnerRepositoryJpa extends GenericRepositoryJpa<Owner, Long> implements OwnerRepository {

}
