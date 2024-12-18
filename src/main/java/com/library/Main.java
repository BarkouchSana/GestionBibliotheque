package com.library;

import com.library.service.BorrowService;
import com.library.service.BookService;
import com.library.service.StudentService;
import com.library.util.DbConnection;
import com.library.model.Book;
import com.library.model.Student;
import com.library.model.Borrow;
import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;  // Importer BorrowDAO
import com.library.dao.StudentDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Création des services
        BookDAO bookDAO = new BookDAO();
        StudentDAO studentDAO = new StudentDAO();
        BorrowDAO borrowDAO = new BorrowDAO();

        BookService bookService = new BookService(bookDAO);
 
        StudentService studentService = new StudentService(studentDAO);
    
        BorrowService borrowService = new BorrowService();  // Passer BorrowDAO au constructeur de BorrowService
        
         
        
        boolean running = true;

        while (running) {
            System.out.println("\n===== Menu =====");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Afficher les livres");
            System.out.println("3. Ajouter un étudiant");
            System.out.println("4. Afficher les étudiants");
            System.out.println("5. Emprunter un livre");
            System.out.println("6. Afficher les emprunts");
            System.out.println("7. Quitter");

            System.out.print("Choisir une option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consommer la ligne restante après l'entier

            switch (choice) {
                case 1:
                    System.out.print("Entrez le titre du livre: ");
                    String title = scanner.nextLine();
                    System.out.print("Entrez l'auteur du livre: ");
                    String author = scanner.nextLine();
                    System.out.print("Entrez le publisher du livre: ");
                    String publisher = scanner.nextLine();
                    System.out.print("Entrez l'annee du livre: ");
                    int annee = scanner.nextInt();
                    Book book = new Book(title, author,publisher,annee, true);
                    bookService.addBook(book);
                    break;

                case 2:
                    bookService.displayBooks();
                    break;

                case 3:
                    System.out.print("Entrez le nom de l'étudiant: ");
                    String studentName = scanner.nextLine();

                    System.out.print("Entrez email de l'étudiant: ");
                    String studentEmail = scanner.nextLine();


                    Student student = new Student(studentName,studentEmail);
                    studentService.addStudent(student);
                    break;

                case 4:
                    studentService.displayStudents();
                    break;

                case 5:
                    System.out.print("Entrez l'ID de l'étudiant: ");
                    int studentId = scanner.nextInt();
                    System.out.print("Entrez l'ID du livre: ");
                    int bookId = scanner.nextInt();
                    Student studentForBorrow = studentService.findStudentById(studentId);
                    Book bookForBorrow = bookService.findBookById(bookId);
                    if (studentForBorrow != null && bookForBorrow != null) {
                        // Créer un objet Borrow avec les informations nécessaires
                        Borrow borrow = new Borrow(studentForBorrow,bookForBorrow, new Date(), null);
                        borrowService.borrowBook(borrow);  // Appel de la méthode avec l'objet Borrow
                    } else {
                        System.out.println("Étudiant ou livre introuvable.");
                    }
                    break;

                case 6:
                    borrowService.displayBorrows();
                    break;

                case 7:
                    running = false;
                    System.out.println("Au revoir!");
                    break;

                default:
                    System.out.println("Option invalide.");
            }
        }

        scanner.close();
    }
}
