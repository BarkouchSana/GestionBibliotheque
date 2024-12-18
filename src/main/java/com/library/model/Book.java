package com.library.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private boolean available;
    // Constructeur par défaut
    public Book() {
    }

    // Constructeur complet
    public Book(int id,String title, String author, String publisher, int year, boolean available) {
        this.id=id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.available=available;
    }

    // Constructeur additionnel si nécessaire
//    public Book(String title, String author,Boolean available) {
//        this.title = title;
//        this.author = author;
//        this.available=available;
//    }
//
//    public Book(int id, String title, String author, boolean available) {
//        this.id = id;
//        this.title = title;
//        this.author = author;
//        this.available = available;
//    }

    public Book(String testTitle, String testAuthor, String testPublisher, int i, boolean b) {
        this.title=testTitle;
        this.author=testAuthor;
        this.publisher=testPublisher;
        this.year=i;
        this.available=b;

    }

    public Book(int i, String effectiveJava, String joshuaBloch) {
        this.id=i;
        this.title=effectiveJava;
        this.author=joshuaBloch;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', author='" + author + "', available=" + available + "}";
    }
}
