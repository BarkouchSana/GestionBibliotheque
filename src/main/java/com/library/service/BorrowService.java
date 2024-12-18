
package com.library.service;

import java.sql.SQLException;
import java.util.List;

import com.library.dao.BookDAO;
import com.library.dao.StudentDAO;
import com.library.dao.BorrowDAO;
import com.library.model.Borrow;

public class BorrowService {

    private BorrowDAO borrowDAO;

    {
        try {
            borrowDAO = new BorrowDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Méthode pour emprunter un livre
    public short borrowBook(Borrow borrow) {
        if (borrow != null && borrow.getStudent() != null && borrow.getBook() != null) {
            borrowDAO.saveBorrow(borrow);
            System.out.println("Livre emprunté avec succès.");
        } else {
            System.out.println("Les informations de l'emprunt sont incomplètes.");
        }
        return 0;
    }

    // Afficher les emprunts (méthode fictive, à adapter)
    public void displayBorrows() throws SQLException {
     List<Borrow> borrows = borrowDAO.getAllBorrows();
        if (borrows.isEmpty()) {
            System.out.println("Aucun emprunt trouvé.");
        } else {
            for (Borrow borrow : borrows) {
                System.out.println(borrow);
            }
        }    }


         /**
     * Méthode pour retourner un livre.
     * @param borrowId L'ID de l'emprunt à retourner.
     */
    public void returnBook(int borrowId) {
        // Implémentez la logique pour mettre à jour la date de retour dans la base de données.
        System.out.println("Fonctionalité à implémenter : retourner un livre.");
    }
    public void addBorrow(Borrow borrow) throws SQLException {
        borrowDAO.saveBorrow(borrow);
    }
    public List<Borrow> getAllBorrows() throws SQLException, SQLException {
        return borrowDAO.getAllBorrows();
    }

    public void updateBorrow(Borrow borrow) throws SQLException {
        borrowDAO.updateBorrow(borrow);
    }
    public void deleteBorrow(int id) throws SQLException {
        borrowDAO.deleteBorrow(id);
    }
}
