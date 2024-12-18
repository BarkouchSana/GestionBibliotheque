//package com.library;
//
//import com.library.model.Book;
//import com.library.service.BookService;
//import org.junit.jupiter.api.*;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class BookServiceTest {
//    private static final BookService bookService = new BookService();
//
//    @Test
//    @Order(1)
//    void testAddBook() {
//        Book book = new Book("Test Title", "Test Author", "Test Publisher", 2024, true);
//        bookService.addBook(book);
//        assertNotNull(book.getId(), "Le livre doit avoir un ID après l'ajout");
//    }
//
//    @Test
//    @Order(2)
//    void testUpdateBook() {
//        Book book = bookService.getAllBooks().get(0);
//        book.setTitle("Updated Title");
//        bookService.updateBook(book);
//
//        Book updatedBook = bookService.findBookById(book.getId());
//        assertEquals("Updated Title", updatedBook.getTitle(), "Le titre doit être mis à jour");
//    }
//
//    @Test
//    @Order(3)
//    void testDeleteBook() {
//        Book book = bookService.getAllBooks().get(0);
//        bookService.deleteBook(book.getId());
//        assertNull(bookService.findBookById(book.getId()), "Le livre doit être supprimé");
//    }
//
//    @Test
//    @Order(4)
//    void testGetAllBooks() {
//        List<Book> books = bookService.getAllBooks();
//        assertNotNull(books, "La liste des livres ne doit pas être nulle");
//    }
//}

package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {
    private BookService bookService;
    private BookDAO bookDAO;

    @BeforeEach
    void setUp() {
        // Création d'un mock de BookDAO
        bookDAO = Mockito.mock(BookDAO.class);
        bookService = new BookService(bookDAO);
    }

    @Test
    void testAddBook() throws SQLException {
        Book book = new Book(1, "Titre de Test", "Auteur de Test", "Éditeur", 2023, true);

        // Appel de la méthode addBook
        bookService.addBook(book);

        // Vérification que bookDAO.add() a été appelé avec le bon argument
        verify(bookDAO, times(1)).add(book);
    }

    @Test
    void testUpdateBook() throws SQLException {
        Book book = new Book(1, "Titre Modifié", "Auteur Modifié", "Éditeur", 2023, false);

        // Appel de la méthode updateBook
        bookService.updateBook(book);

        // Vérification que bookDAO.update() a été appelé avec le bon argument
        verify(bookDAO, times(1)).update(book);
    }

    @Test
    void testDeleteBook() throws SQLException {
        int bookId = 1;

        // Appel de la méthode deleteBook
        bookService.deleteBook(bookId);

        // Vérification que bookDAO.delete() a été appelé avec le bon argument
        verify(bookDAO, times(1)).delete(bookId);
    }

    @Test
    void testGetAllBooks() throws SQLException {
        List<Book> mockBooks = Arrays.asList(
                new Book(1, "Titre 1", "Auteur 1", "Éditeur 1", 2022, true),
                new Book(2, "Titre 2", "Auteur 2", "Éditeur 2", 2023, false)
        );

        // Simulation du comportement de bookDAO.findAll()
        when(bookDAO.findAll()).thenReturn(mockBooks);

        // Appel de la méthode getAllBooks
        List<Book> books = bookService.getAllBooks();

        // Assertions
        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("Titre 1", books.get(0).getTitle());
        assertEquals("Titre 2", books.get(1).getTitle());

        // Vérification que bookDAO.findAll() a été appelé une fois
        verify(bookDAO, times(1)).findAll();
    }

    @Test
    void testFindBookById() throws SQLException {
        int bookId = 1;
        Book mockBook = new Book(1, "Titre Unique", "Auteur Unique", "Éditeur", 2023, true);

        // Simulation du comportement de bookDAO.findById()
        when(bookDAO.findById(bookId)).thenReturn(mockBook);

        // Appel de la méthode findBookById
        Book book = bookService.findBookById(bookId);

        // Assertions
        assertNotNull(book);
        assertEquals("Titre Unique", book.getTitle());
        assertEquals("Auteur Unique", book.getAuthor());

        // Vérification que bookDAO.findById() a été appelé avec le bon argument
        verify(bookDAO, times(1)).findById(bookId);
    }
}
