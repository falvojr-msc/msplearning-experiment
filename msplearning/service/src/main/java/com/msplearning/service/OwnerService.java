package com.msplearning.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msplearning.entity.Owner;
import com.msplearning.entity.User;
import com.msplearning.entity.common.BusinessException;
import com.msplearning.repository.OwnerRepository;
import com.msplearning.repository.generic.GenericRepository;
import com.msplearning.service.generic.GenericCrudService;

/**
 * The UserService class provides the business operations of entity {@link User}.
 *
 * @author Venilton Falvo Junior (veniltonjr)
 */
@Service("ownerService")
public class OwnerService extends GenericCrudService<Owner, Long> implements InitializingBean {

	@Autowired
	private OwnerRepository ownerRepository;

	@Override
	protected GenericRepository<Owner, Long> getRepository() {
		return this.ownerRepository;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			super.findById(1L);
		} catch (BusinessException e) {
			this.ownerRepository.insert(new Owner(1L, "Andre Luis Campopiano"));
			this.ownerRepository.insert(new Owner(2L, "Andre Luiz Bacaglini"));
			this.ownerRepository.insert(new Owner(3L, "Andre Luiz dos Santos"));
			this.ownerRepository.insert(new Owner(4L, "Arthur Godoy"));
			this.ownerRepository.insert(new Owner(5L, "Douglas Dionizio"));
			this.ownerRepository.insert(new Owner(6L, "Douglas Santos"));
			this.ownerRepository.insert(new Owner(7L, "Eduardo Scamilhe"));
			this.ownerRepository.insert(new Owner(8L, "Erik Comelli"));
			this.ownerRepository.insert(new Owner(9L, "Fabricio Roncalio"));
			this.ownerRepository.insert(new Owner(10L, "Felipe Camara"));
			this.ownerRepository.insert(new Owner(11L, "Francinei Marin"));
			this.ownerRepository.insert(new Owner(12L, "Gustavo Bergamim"));
			this.ownerRepository.insert(new Owner(13L, "Janaiza Correia"));
			this.ownerRepository.insert(new Owner(14L, "Jaqueline Mendes"));
			this.ownerRepository.insert(new Owner(15L, "Lais Garcia"));
			this.ownerRepository.insert(new Owner(16L, "Leonardo Bononi"));
			this.ownerRepository.insert(new Owner(17L, "Luis Gustavi Maturo"));
			this.ownerRepository.insert(new Owner(18L, "Odair Viol"));
			this.ownerRepository.insert(new Owner(19L, "Paulo Barros"));
			this.ownerRepository.insert(new Owner(20L, "Rodrigo Mendes"));
			this.ownerRepository.insert(new Owner(21L, "Adriano Bocaletti"));
		}
	}
}