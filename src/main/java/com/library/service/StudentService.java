package com.library.service;

import com.library.dao.StudentDAO;
import com.library.model.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private  StudentDAO studentDAO;
    // Initialisation via constructeur
    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public Student findStudentById(int id) {
        return studentDAO.findStudentById(id);
    }
    /**
     * Ajouter un nouvel étudiant.
     * @param student L'étudiant à ajouter.
     */

    public void addStudent(Student student) {
        if (student == null || student.getName() == null || student.getEmail() == null) {
            throw new IllegalArgumentException("Les informations de l'étudiant sont incomplètes.");
        }
        studentDAO.addStudent(student);
        System.out.println("Étudiant ajouté avec succès : " + student);
    }



     /**
     * Récupérer un étudiant par ID.
     * @param id L'ID de l'étudiant.
     * @return L'étudiant correspondant ou null si non trouvé.
     */
     public Student getStudentById(int id) {
         return studentDAO.getStudentById(id);
     }

    // Afficher tous les étudiants
    public void displayStudents() {
        List<Student> students = studentDAO.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("Aucun étudiant trouvé.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }



    public void updateStudent(Student student) {
        if (student != null && student.getId() > 0) {
            studentDAO.updateStudent(student);
            System.out.println("Étudiant mis à jour avec succès.");
        } else {
            System.out.println("Informations invalides pour la mise à jour.");
        }
    }

    public void deleteStudent(int id) {
        if (id > 0) {
            studentDAO.deleteStudent(id);
            System.out.println("Étudiant supprimé avec succès.");
        } else {
            System.out.println("ID invalide pour la suppression.");
        }
    }
}
