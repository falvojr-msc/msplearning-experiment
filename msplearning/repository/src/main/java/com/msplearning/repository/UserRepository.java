package com.msplearning.repository;

import java.util.List;

import com.msplearning.entity.User;
import com.msplearning.repository.generic.GenericRepository;
import com.msplearning.repository.jpa.UserRepositoryJpa;

/**
 * Interface of {@link UserRepositoryJpa}.
 * 
 * @author Venilton Falvo Junior (veniltonjr)
 */
public interface UserRepository extends GenericRepository<User, Long> {

	User authenticate(String email, String password);

	List<User> findByOwner(Long idOwner);
}
