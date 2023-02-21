package com.bank.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.rest.common.messages.BaseResponse;
import com.bank.rest.dto.EmployeeDTO;
import com.bank.rest.service.EmployeeService;

@Validated
@RestController
@RequestMapping("v1/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	// finding list of all employees
	@GetMapping(value = "/getAll")
	public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
		List<EmployeeDTO> list = employeeService.getEmployeesList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	// In this api have used request param to show you 1 way of creating api 
	// and in delete method have used another way which is path variable
	@GetMapping(value = "/get/by-id")
	public ResponseEntity<EmployeeDTO> getEmployeeById(@RequestParam Long id) {
		EmployeeDTO list = employeeService.getByEmployeeId(id);
		return new ResponseEntity<EmployeeDTO>(list, HttpStatus.OK);
	}

	// saving and updating employee into the application in single method only, 
	// we can create separate method by using PUT for updating records as well
	@PostMapping(value = { "/add", "/update" })
	public ResponseEntity<BaseResponse> createOrUpdateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
		BaseResponse response = employeeService.saveOrUpdateEmployee(employeeDTO);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// deleting employee record
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<BaseResponse> deleteEmployeeById(@PathVariable("id") Long id) {
		BaseResponse response = employeeService.deleteEmployeeById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
