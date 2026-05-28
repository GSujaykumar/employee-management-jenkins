package com.example.employeemanagement;

import com.example.employeemanagement.controller.EmployeeController;
import com.example.employeemanagement.service.EmployeeService;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import com.example.employeemanagement.dto.EmployeeDTO;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(EmployeeController.class)   //loads only the controller
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc; //Fake http client

    @MockBean
    private EmployeeService employeeService; //creating Fake Service objects

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllEmployees() throws Exception {

        EmployeeDTO employee1 = EmployeeDTO.builder()
                .id(1L)
                .name("Sujay")
                .email("sujay@gmail.com")
                .department("IT")
                .salary(50000.0)
                .build();

        EmployeeDTO employee2 = EmployeeDTO.builder()
                .id(2L)
                .name("Rahul")
                .email("rahul@gmail.com")
                .department("HR")
                .salary(40000.0)
                .build();

        List<EmployeeDTO> employeeList =
                List.of(employee1, employee2);

        when(employeeService.getAllEmployees())
                .thenReturn(employeeList);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name")
                        .value("Sujay"))
                .andExpect(jsonPath("$[1].name")
                        .value("Rahul"));
    }

    @Test
    void testGetEmployeeById() throws Exception {

        EmployeeDTO employee = EmployeeDTO.builder()
                .id(1L)
                .name("Sujay")
                .email("sujay@gmail.com")
                .department("IT")
                .salary(50000.0)
                .build();

        when(employeeService.getEmployeeById(1L))
                .thenReturn(employee);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Sujay"))
                .andExpect(jsonPath("$.department")
                        .value("IT"))
                .andExpect(jsonPath("$.salary")
                        .value(50000.0));
    }


    @Test
    void testCreateEmployee() throws Exception {

        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .id(1L)
                .name("Sujay")
                .email("sujay@gmail.com")
                .department("IT")
                .salary(50000.0)
                .build();

        when(employeeService.createEmployee(any(EmployeeDTO.class)))
                .thenReturn(employeeDTO);

        mockMvc.perform(post("/api/employees")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Sujay"))
                .andExpect(jsonPath("$.department")
                        .value("IT"));
    }

    @Test
    void testUpdateEmployee() throws Exception {

        EmployeeDTO updatedDTO = EmployeeDTO.builder()
                .id(1L)
                .name("Rahul")
                .email("rahul@gmail.com")
                .department("HR")
                .salary(60000.0)
                .build();

        when(employeeService.updateEmployee(eq(1L), any(EmployeeDTO.class)))
                .thenReturn(updatedDTO);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Rahul"))
                .andExpect(jsonPath("$.department")
                        .value("HR"))
                .andExpect(jsonPath("$.salary")
                        .value(60000.0));
    }


    @Test
    void testDeleteEmployee() throws Exception {

        Mockito.doNothing().when(employeeService)
                .deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk());

        verify(employeeService, Mockito.times(1))
                .deleteEmployee(1L);
    }
}