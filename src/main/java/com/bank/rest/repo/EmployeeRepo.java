package com.bank.rest.repo;

import com.bank.rest.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Jigyasu Garg
 * @since 20 02 23
 */

public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Long> {

	public EmployeeEntity findByEmployeeId(Long empId);

}
