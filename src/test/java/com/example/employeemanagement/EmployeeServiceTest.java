package com.example.employeemanagement;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.service.impl.EmployeeServiceImpl;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;



    @Test
    void testCreateEmployee() {

        Employee employee = Employee.builder()
                .id(1L)
                .name("Sujay")
                .email("sujay@gmail.com")
                .department("IT")
                .salary(50000.0)
                .build();

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee);

        EmployeeDTO dto = EmployeeDTO.builder()
                .name("Sujay")
                .email("sujay@gmail.com")
                .department("IT")
                .salary(50000.0)
                .build();

        EmployeeDTO savedEmployee =
                employeeService.createEmployee(dto);

        assertNotNull(savedEmployee);

        assertEquals("Sujay", savedEmployee.getName());

        assertEquals("IT", savedEmployee.getDepartment());


        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testGetAllEmployees() {

        Employee employee = Employee.builder()
                .id(1l)
                .name("Sujay")
                .email("sujay@gmail.com")
                .department("IT")
                .salary(50000.0)
                .build();

        Employee employee1 = Employee.builder()
                .id(2L)
                .name("Rahul")
                .email("rahul@gmail.com")
                .department("HR")
                .salary(40000.0)
                .build();

        List<Employee> employeeList =
                List.of(employee,employee1 );

        when(employeeRepository.findAll()).thenReturn(employeeList);

        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployees();

        assertEquals(2, employeeDTOList.size());

        assertEquals("Sujay", employeeDTOList.get(0).getName());
        assertEquals("Rahul", employeeDTOList.get(1).getName());

        verify(employeeRepository, times(1)).findAll();

    }

    @Test
    void testGetEmployeeById() {

        Employee employee = Employee.builder()
                .id(1L)
                .name("Sujay")
                .email("sujay@gmail.com")
                .department("IT")
                .salary(50000.0)
                .build();

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        EmployeeDTO dto =
                employeeService.getEmployeeById(1L);

        assertNotNull(dto);

        assertEquals("Sujay", dto.getName());

        assertEquals("IT", dto.getDepartment());

        verify(employeeRepository, times(1)).findById(1L);

    }

    @Test
    void testUpdateEmployee() {

        Employee existingEmployee = Employee.builder()
                .id(1L)
                .name("Sujay")
                .email("sujay@gmail.com")
                .department("IT")
                .salary(50000.0)
                .build();

        EmployeeDTO updatedDTO = EmployeeDTO.builder()
                .name("Rahul")
                .email("rahul@gmail.com")
                .department("HR")
                .salary(60000.0)
                .build();

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(existingEmployee));

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(existingEmployee);

        EmployeeDTO result =
                employeeService.updateEmployee(1L, updatedDTO);

        assertNotNull(result);

        assertEquals("Rahul", result.getName());

        assertEquals("HR", result.getDepartment());

        assertEquals(60000.0, result.getSalary());

        verify(employeeRepository, times(1))
                .findById(1L);

        verify(employeeRepository, times(1))
                .save(any(Employee.class));
    }

    @Test
    void testDeleteEmployee() {


        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1))
                .deleteById(1L);
    }
}
