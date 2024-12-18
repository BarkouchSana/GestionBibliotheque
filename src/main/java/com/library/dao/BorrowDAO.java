//
//package com.library.dao;
//
//import com.library.model.Book;
//import com.library.model.Borrow;
//import com.library.model.Student;
//import com.library.util.DbConnection;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class BorrowDAO {
//
//    // Assuming you have a data source or a connection method
//    private Connection connection;
//
//    public BorrowDAO() throws SQLException {
//        // Initialize your connection, for example:
//        connection = DbConnection.getConnection(); // Replace with your actual connection
//    }
//    public Borrow getBorrowById(int borrowId) {
//        Borrow borrow = null;
//        String query = "SELECT * FROM borrows WHERE borrow_id = ?"; // Adjust to your table schema
//
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, borrowId);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                // Retrieve data from the result set and map it to the Borrow object
//                int id = resultSet.getInt("borrow_id");
//                Student student = new Student(resultSet.getInt("student_id"), resultSet.getString("student_name"), resultSet.getString("student_email"));
//                Book book = new Book(resultSet.getInt("book_id"), resultSet.getString("book_title"), resultSet.getString("book_author"), resultSet.getString("publisher"),resultSet.getInt("year"), resultSet.getBoolean("is_available"));
//                Date borrowDate = resultSet.getDate("borrow_date");
//                Date returnDate = resultSet.getDate("return_date");
//
//                borrow = new Borrow(id, student, book, borrowDate, returnDate);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle the exception properly
//        }
//
//        return borrow;
//    }
//    public List<Borrow> getAllBorrows() throws SQLException {
//        List<Borrow> borrows = new ArrayList<>();
//        String query = "SELECT * FROM borrows";
//        try (Connection connection = DbConnection.getConnection();
//             Statement stmt = connection.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//            while (rs.next()) {
//                Borrow borrow = new Borrow(rs.getInt("id"),
//                        new Student(rs.getInt("student_id"), "Student Name", "student@example.com"),
//                        new Book(rs.getInt("book_id"), "Book Title", "Author", true),
//                        rs.getDate("borrow_date"), rs.getDate("return_date"));
//                borrows.add(borrow);
//            }
//        }
//        return borrows;
//    }
//
//public void save(Borrow borrow) {
//    String query = "INSERT INTO borrows (student_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
//
//    try (Connection connection = DbConnection.getConnection();
//         PreparedStatement stmt = connection.prepareStatement(query)) {
//
//        stmt.setInt(1, borrow.getStudent().getId());
//        stmt.setInt(2, borrow.getBook().getId());
//        stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
//        stmt.setDate(4, borrow.getReturnDate() != null ? new java.sql.Date(borrow.getReturnDate().getTime()) : null);
//
//        stmt.executeUpdate();
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}
//
//
//    public void updateBorrow(Borrow borrow) throws SQLException {
//        String query = "UPDATE borrows SET student_id=?, book_id=?, borrow_date=?, return_date=? WHERE id=?";
//        try (Connection connection = DbConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setInt(1, borrow.getStudent().getId());
//            stmt.setInt(2, borrow.getBook().getId());
//            stmt.setDate(3, new Date(borrow.getBorrowDate().getTime()));
//            stmt.setDate(4, new Date(borrow.getReturnDate().getTime()));
//            stmt.setInt(5, borrow.getId());
//            stmt.executeUpdate();
//        }
//    }
//
//    public void deleteBorrow(int id) throws SQLException {
//        String query = "DELETE FROM borrows WHERE id=?";
//        try (Connection connection = DbConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setInt(1, id);
//            stmt.executeUpdate();
//        }
//    }
//    public void addBorrow(Borrow borrow) {
//        save(borrow);
//    }
//
//
//}
package com.library.dao;

import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {

    private final Connection connection;

    public BorrowDAO() throws SQLException {
        connection = DbConnection.getConnection(); // Initialize the database connection
    }

    public Borrow getBorrowById(int borrowId) {
        Borrow borrow = null;
        String query = "SELECT * FROM borrows WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, borrowId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    borrow = mapBorrow(resultSet); // Map the result set to a Borrow object
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Borrow by ID: " + e.getMessage());
        }
        return borrow;
    }

    public List<Borrow> getAllBorrows() {
        List<Borrow> borrows = new ArrayList<>();
        String query = "SELECT * FROM borrows";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                borrows.add(mapBorrow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all Borrows: " + e.getMessage());
        }
        return borrows;
    }

    public void saveBorrow(Borrow borrow) {
        String query = "INSERT INTO borrows (student_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, borrow.getStudent().getId());
            stmt.setInt(2, borrow.getBook().getId());
            stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(4, borrow.getReturnDate() != null ? new java.sql.Date(borrow.getReturnDate().getTime()) : null);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving Borrow: " + e.getMessage());
        }
    }

    public void updateBorrow(Borrow borrow) {
        String query = "UPDATE borrows SET student_id = ?, book_id = ?, borrow_date = ?, return_date = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, borrow.getStudent().getId());
            stmt.setInt(2, borrow.getBook().getId());
            stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(4, borrow.getReturnDate() != null ? new java.sql.Date(borrow.getReturnDate().getTime()) : null);
            stmt.setInt(5, borrow.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating Borrow: " + e.getMessage());
        }
    }

    public void deleteBorrow(int id) {
        String query = "DELETE FROM borrows WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting Borrow: " + e.getMessage());
        }
    }

    private Borrow mapBorrow(ResultSet rs) throws SQLException {
        // Récupération des données de la table "borrows"
        int id = rs.getInt("borrow_id");

        // Création de l'objet Student à partir des données de la table
        Student student = new Student(

                rs.getString("name"),
                rs.getString("email")
        );

        // Création de l'objet Book à partir des données de la table
        Book book = new Book(
                rs.getInt("book_id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("publisher"),
                rs.getInt("year"),
                rs.getBoolean("is_available")
        );

        // Récupération des dates de prêt et de retour
        Date borrowDate = rs.getDate("borrow_date");
        Date returnDate = rs.getDate("return_date");

        // Retour de l'objet Borrow construit
        return new Borrow(id, student, book, borrowDate, returnDate);
    }

}
