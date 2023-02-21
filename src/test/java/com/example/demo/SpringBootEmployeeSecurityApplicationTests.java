package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.rest.entity.EmployeeEntity;
import com.bank.rest.repo.EmployeeRepo;
import com.bank.rest.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootEmployeeSecurityApplicationTests {
	
	@Autowired
	private EmployeeService empService;
	
	@MockBean
	private EmployeeRepo empRepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void getEmplyeesTest() {
		((Stream) when(empRepository.findAll()).thenReturn( (List<EmployeeEntity>) Stream.of(
				new EmployeeEntity(1L,"Sandeep","Maheswari","sandeep@gmail.com",32,"IT"),
				new EmployeeEntity(2L,"Sameer","Singh","ssingh@gmail.com",37,"HR"))))
		.collect(Collectors.toList());
		assertEquals(2, empService.getEmployeesList().size());
	}
	
	@Test
	public void getByEmployeeIdTest() {
		Long empId = 1L;
		when(empRepository.findByEmployeeId(empId)).thenReturn((EmployeeEntity) Stream.of(
				new EmployeeEntity(1L,"Sandeep","Maheswari","sandeep@gmail.com",32,"IT")).collect(Collectors.toList()));
		assertEquals(1, empService.getByEmployeeId(empId));
	}
	
	@Test
	public void saveEmployeeTest() {
		EmployeeEntity employee = new EmployeeEntity(1L,"Sandeep","Maheswari","sandeep@gmail.com",32,"IT");
		when(empRepository.save(employee)).thenReturn(employee);
		assertEquals(employee, empService.saveOrUpdateEmployee(empService.copyEmployeeEntityToDto(employee)));
	}

	@Test
	public void deleteEmployeeTest() {
		Long empId = 1L;
		empService.deleteEmployeeById(empId);
		verify(empRepository,times(1)).deleteById(empId);
	}
	
	@Test
	void contextLoads() {
	}

}
