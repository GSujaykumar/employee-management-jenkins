package com.example.employeemanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EmployeeManagementApplicationTests {

    @Test
    void testAddition() {
        int result = 2 + 3;
        assertEquals(5, result);
    }
}