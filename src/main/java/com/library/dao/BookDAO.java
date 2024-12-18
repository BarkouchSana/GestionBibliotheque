package com.library.dao;

import com.library.model.Book;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // Assuming you have a data source or a connection method
    private Connection connection;

    public BookDAO() throws SQLException {
        // Initialize your connection, for example:
        connection = DbConnection.getConnection(); // Replace with your actual connection
    }

    // Ajouter un nouveau livre dans la base de données
//    public void add(Book book) {
//        String sql = "INSERT INTO books (title, author, publisher, year, available) VALUES (?, ?, ?, ?, ?)";
//        try (Connection connection = DbConnection.getConnection();
//                PreparedStatement statement = connection.prepareStatement(sql)) {
//
//            statement.setString(1, book.getTitle());
//            statement.setString(2, book.getAuthor());
//            statement.setString(3, book.getPublisher());
//            statement.setInt(4, book.getYear());
//            statement.setBoolean(5, book.isAvailable());
//
//            int rowsInserted = statement.executeUpdate();
//            if (rowsInserted > 0) {
//                System.out.println("Livre inséré avec succès !");
//            }
//        } catch (SQLException e) {
//            System.err.println("Erreur lors de l'ajout du livre : " + e.getMessage());
//        }
//    }
    public void add(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author, publisher, year, available) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getYear());
            statement.setBoolean(5, book.isAvailable());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Book getBookById(int id) {
        String query = "SELECT * FROM books WHERE book_id = ?";
        try (Connection connection = DbConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Book(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("author"),
                            resultSet.getString("publisher"),
                            resultSet.getInt("year"),
                            resultSet.getBoolean("available")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving book by ID: " + e.getMessage());
        }
        return null;
    }
    public Book findById(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE book_id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToBook(resultSet);
                }
            }
        }
        return null;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                books.add(new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getInt("year"),
                        resultSet.getBoolean("available")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les livres : " + e.getMessage(), e);
        }
        return books;
    }

    public List<Book> findAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                books.add(mapRowToBook(resultSet));
            }
        }
        return books;
    }

    private Book mapRowToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("book_id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPublisher(resultSet.getString("publisher"));
        book.setYear(resultSet.getInt("year"));
        book.setAvailable(resultSet.getBoolean("available"));
        return book;
    }


    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publisher = ?, year = ?, available = ? WHERE book_id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getYear());
            statement.setBoolean(5, book.isAvailable());
            statement.setInt(6, book.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du livre : " + e.getMessage(), e);
        }
    }

    // Delete a book by its ID
    public void delete(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du livre : " + e.getMessage(), e);
        }
    }
}
