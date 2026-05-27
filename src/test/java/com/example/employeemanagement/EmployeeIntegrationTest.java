package com.example.employeemanagement;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void shouldCreateEmployee() throws Exception {

        EmployeeDTO dto = EmployeeDTO.builder()
                .name("Sujay")
                .email("sujay+create@gmail.com")
                .department("IT")
                .salary(50000.0)
                .build();

        mockMvc.perform(post("/api/employees")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Sujay"))
                .andExpect(jsonPath("$.email").value("sujay+create@gmail.com"))
                .andExpect(jsonPath("$.department").value("IT"))
                .andExpect(jsonPath("$.salary").value(50000.0));
    }

    @Test
    void shouldGetAllEmployees() throws Exception {

        long before = employeeRepository.count();

        employeeRepository.save(Employee.builder()
                .name("Sujay")
                .email("sujay+all1@gmail.com")
                .department("IT")
                .salary(50000.0)
                .build());

        employeeRepository.save(Employee.builder()
                .name("Rahul")
                .email("rahul+all2@gmail.com")
                .department("HR")
                .salary(40000.0)
                .build());

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize((int) before + 2)));
    }

    @Test
    void shouldGetEmployeeById() throws Exception {

        Employee saved = employeeRepository.save(Employee.builder()
                .name("Sujay")
                .email("sujay+byid@gmail.com")
                .department("IT")
                .salary(50000.0)
                .build());

        mockMvc.perform(get("/api/employees/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.name").value("Sujay"))
                .andExpect(jsonPath("$.department").value("IT"))
                .andExpect(jsonPath("$.salary").value(50000.0));
    }

    @Test
    void shouldUpdateEmployee() throws Exception {

        Employee saved = employeeRepository.save(Employee.builder()
                .name("Sujay")
                .email("sujay+update@gmail.com")
                .department("IT")
                .salary(50000.0)
                .build());

        EmployeeDTO update = EmployeeDTO.builder()
                .name("Rahul")
                .email("rahul+update@gmail.com")
                .department("HR")
                .salary(60000.0)
                .build();

        mockMvc.perform(put("/api/employees/" + saved.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.name").value("Rahul"))
                .andExpect(jsonPath("$.department").value("HR"))
                .andExpect(jsonPath("$.salary").value(60000.0));

        Employee fromDb = employeeRepository.findById(saved.getId()).orElseThrow();
        assert "Rahul".equals(fromDb.getName());
        assert "HR".equals(fromDb.getDepartment());
    }

    @Test
    void shouldDeleteEmployee() throws Exception {

        Employee saved = employeeRepository.save(Employee.builder()
                .name("Sujay")
                .email("sujay+delete@gmail.com")
                .department("IT")
                .salary(50000.0)
                .build());

        mockMvc.perform(delete("/api/employees/" + saved.getId()))
                .andExpect(status().isOk());

        assert employeeRepository.findById(saved.getId()).isEmpty();
    }

    @Test
    void shouldRunFullCrudFlow() throws Exception {

        EmployeeDTO dto = EmployeeDTO.builder()
                .name("Sujay")
                .email("sujay+flow@gmail.com")
                .department("IT")
                .salary(50000.0)
                .build();

        String response = mockMvc.perform(post("/api/employees")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        EmployeeDTO created = objectMapper.readValue(response, EmployeeDTO.class);
        Long id = created.getId();

        mockMvc.perform(get("/api/employees/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sujay"));

        EmployeeDTO update = EmployeeDTO.builder()
                .name("Sujay R")
                .email("sujay.r+flow@gmail.com")
                .department("Platform")
                .salary(80000.0)
                .build();

        mockMvc.perform(put("/api/employees/" + id)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sujay R"))
                .andExpect(jsonPath("$.salary").value(80000.0));

        mockMvc.perform(delete("/api/employees/" + id))
                .andExpect(status().isOk());

        assert employeeRepository.findById(id).isEmpty();
    }
}
