package com.library;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import com.library.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    private StudentDAO studentDAO;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentDAO = mock(StudentDAO.class); // Mock de DAO
        studentService = new StudentService(studentDAO); // Injection du mock
    }

    @Test
    void testAddStudent_ValidStudent() {
        Student student = new Student(1, "John Doe", "john.doe@example.com");
        doNothing().when(studentDAO).addStudent(student);

        studentService.addStudent(student);

        verify(studentDAO, times(1)).addStudent(student);
    }

    @Test
    void testAddStudent_InvalidStudent() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                studentService.addStudent(new Student()) // Utilisez un étudiant invalide
        );

        assertEquals("Les informations de l'étudiant sont incomplètes.", exception.getMessage());
    }



    @Test
    void testGetAllStudents_NoStudents() {
        when(studentDAO.getAllStudents()).thenReturn(Collections.emptyList());

        List<Student> students = studentService.getAllStudents();

        assertTrue(students.isEmpty());
        verify(studentDAO, times(1)).getAllStudents();
    }

    @Test
    void testFindStudentById_ValidId() {
        Student student = new Student(1, "Jane Doe", "jane.doe@example.com");
        when(studentDAO.findStudentById(1)).thenReturn(student);

        Student result = studentService.findStudentById(1);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        verify(studentDAO, times(1)).findStudentById(1);
    }
}
