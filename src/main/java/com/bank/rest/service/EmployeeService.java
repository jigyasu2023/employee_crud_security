package com.bank.rest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.rest.common.exceptions.CustomDataIntegrityViolationException;
import com.bank.rest.common.exceptions.RecordNotFoundException;
import com.bank.rest.common.messages.BaseResponse;
import com.bank.rest.common.messages.CustomMessage;
import com.bank.rest.common.utils.Topic;
import com.bank.rest.dto.EmployeeDTO;
import com.bank.rest.entity.EmployeeEntity;
import com.bank.rest.repo.EmployeeRepo;
/**
 *
 * @author Jigyasu Garg
 * @since 20 02 23
 */

@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	public List<EmployeeDTO> getEmployeesList() {
		return employeeRepo.findAll().stream().map(this::copyEmployeeEntityToDto).collect(Collectors.toList());
	}

	public EmployeeDTO getByEmployeeId(Long employeeId) {
		if (employeeRepo.existsById(employeeId)) {
			EmployeeEntity employeeEntity = employeeRepo.findByEmployeeId(employeeId);
			return copyEmployeeEntityToDto(employeeEntity);
		}else {
			throw new RecordNotFoundException(CustomMessage.DOESNOT_EXIT + employeeId);
		}
		
	}

	public BaseResponse saveOrUpdateEmployee(EmployeeDTO employeeDTO) {
		try {
			EmployeeEntity employeeEntity = new EmployeeEntity();
			BeanUtils.copyProperties(employeeDTO, employeeEntity);
			employeeRepo.save(employeeEntity);
		}  catch (DataIntegrityViolationException ex) {
			throw new CustomDataIntegrityViolationException(ex.getCause().getCause().getMessage());
		}
		return new BaseResponse(Topic.EMPLOYEE.getName() + CustomMessage.SAVE_SUCCESS_MESSAGE);
	}

	public BaseResponse deleteEmployeeById(Long employeeId) {
		if (employeeRepo.existsById(employeeId)) {
			employeeRepo.deleteById(employeeId);
		} else {
			throw new RecordNotFoundException(CustomMessage.NO_RECOURD_FOUND + employeeId);
		}
		return new BaseResponse(Topic.EMPLOYEE.getName() + CustomMessage.DELETE_SUCCESS_MESSAGE);
	}

	public EmployeeDTO copyEmployeeEntityToDto(EmployeeEntity employeeEntity) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		BeanUtils.copyProperties(employeeEntity, employeeDTO);
		return employeeDTO;
	}

}
