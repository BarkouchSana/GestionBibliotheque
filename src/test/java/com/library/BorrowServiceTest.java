package com.library;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.library.dao.BorrowDAO;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.model.Book;

import com.library.service.BorrowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowServiceTest {

    @Mock
    private BorrowDAO borrowDAO;

    @InjectMocks
    private BorrowService borrowService;

    private Borrow borrow;
    private Student student;
    private Book book;

    @BeforeEach
    public void setUp() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this);

        // Create sample objects
        student = new Student(1, "John Doe", "john.doe@example.com");
        book = new Book(1, "Effective Java", "Joshua Bloch");
        borrow = new Borrow(1, student, book, null, null); // Borrow date and return date can be mocked later if needed
    }

    @Test
    public void testBorrowBook_Valid() {
        // Arrange
        doNothing().when(borrowDAO).saveBorrow(borrow); // Simule l'appel de la méthode void

        // Act
        short result = borrowService.borrowBook(borrow);

        // Assert
        assertEquals(0, result); // Vérifiez que la méthode renvoie 0
        verify(borrowDAO, times(1)).saveBorrow(borrow); // Vérifiez que saveBorrow a été appelé une fois
    }


    @Test
    public void testBorrowBook_Invalid() {
        // Arrange
        Borrow invalidBorrow = new Borrow(); // Invalid because student and book are null

        // Act
        short result = borrowService.borrowBook(invalidBorrow);

        // Assert
        assertEquals(0, result); // Assuming method returns 0 even for invalid borrow
    }

    @Test
    public void testDisplayBorrows_Empty() throws SQLException {
        // Arrange
        List<Borrow> emptyBorrows = new ArrayList<>();
        when(borrowDAO.getAllBorrows()).thenReturn(emptyBorrows);

        // Act and Assert
        borrowService.displayBorrows(); // It should print "Aucun emprunt trouvé."
    }

    @Test
    public void testDisplayBorrows_NotEmpty() throws SQLException {
        // Arrange
        List<Borrow> borrows = new ArrayList<>();
        borrows.add(borrow); // Add a valid borrow
        when(borrowDAO.getAllBorrows()).thenReturn(borrows);

        // Act
        borrowService.displayBorrows(); // It should print the borrow details
    }

    @Test
    public void testAddBorrow() throws SQLException {
        // Act
        borrowService.addBorrow(borrow);

        // Assert
        verify(borrowDAO, times(1)).saveBorrow(borrow); // Verify saveBorrow is called once
    }

    @Test
    public void testUpdateBorrow() throws SQLException {
        // Act
        borrowService.updateBorrow(borrow);

        // Assert
        verify(borrowDAO, times(1)).updateBorrow(borrow); // Verify updateBorrow is called once
    }

    @Test
    public void testDeleteBorrow() throws SQLException {
        // Act
        borrowService.deleteBorrow(1);

        // Assert
        verify(borrowDAO, times(1)).deleteBorrow(1); // Verify deleteBorrow is called once
    }

    @Test
    public void testReturnBook() {
        // This is just a placeholder as the returnBook method is not implemented yet
        borrowService.returnBook(1);
        // Assert that it prints the placeholder message
    }
}
