package com.library.service;
import com.library.dao.BookDAO; // Importation de BookDAO
import com.library.model.Book;   // Importation de Book

import java.sql.SQLException;
import java.util.List;


public class BookService {
    private  BookDAO bookDAO;

   public BookService(BookDAO bookDAO) {
       this.bookDAO = bookDAO;
   }


    public void addBook(Book book) {
        try {
            bookDAO.add(book);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du livre", e);
        }
    }

    public List<Book> getAllBooks() {
        try {
            return bookDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des livres", e);
        }
    }

    public Book findBookById(int id) {
        try {
            return bookDAO.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du livre", e);
        }
    }
    public void deleteBook(int id) {

            bookDAO.delete(id);

    }
    public void updateBook(Book book) {

            bookDAO.update(book);

    }


    public void displayBooks() {
        List<Book> books = null;
        try {
            books = bookDAO.findAll();
        } catch (SQLException e) {

        }
        for (Book book : books) {
            System.out.println(book);
        }
    }
}
