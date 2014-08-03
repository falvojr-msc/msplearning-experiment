package com.msplearning.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msplearning.entity.User;
import com.msplearning.entity.common.BusinessException;
import com.msplearning.repository.UserRepository;
import com.msplearning.repository.generic.GenericRepository;
import com.msplearning.service.generic.GenericCrudService;

/**
 * The UserService class provides the business operations of entity {@link User}.
 *
 * @author Venilton Falvo Junior (veniltonjr)
 */
@Service("userService")
public class UserService extends GenericCrudService<User, Long> {

	@Autowired
	private UserRepository userRepository;

	public User authenticate(String email, String password) throws BusinessException {
		User entity = this.userRepository.authenticate(email, password);
		if (entity == null) {
			throw new BusinessException(super.getMessage("R.message.001"));
		} else {
			entity.setDateLastLogin(new Date());
			this.update(entity);
		}
		return entity;
	}

	@Override
	public void insert(User entity) throws BusinessException {
		entity.setId(null);
		entity.setDateRegistration(new Date());
		entity.setDateLastLogin(new Date());
		
		super.insert(entity);
	}
	
	@Override
	protected GenericRepository<User, Long> getRepository() {
		return userRepository;
	}

	public List<User> findByOwner(Long idOwner) {
		return userRepository.findByOwner(idOwner);
	}
}