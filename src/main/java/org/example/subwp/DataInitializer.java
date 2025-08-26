package org.example.subwp;

import org.example.subwp.model.Author;
import org.example.subwp.model.Book;
import org.example.subwp.model.Category;
import org.example.subwp.service.AuthorService;
import org.example.subwp.service.BookService;
import org.example.subwp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;

    @Override
    public void run(String... args) throws Exception {
        if (!authorService.getAllAuthors().isEmpty()) {
            System.out.println("Baza podataka već sadrži podatke. Preskačem inicijalizaciju.");
            return;
        }
        Author author1 = new Author();
        author1.setFirstName("Meša");
        author1.setLastName("Selimović");
        author1.setBiography("Bosanski pisac, autor romana Derviš i smrt");
        authorService.saveAuthor(author1);

        Author author2 = new Author();
        author2.setFirstName("Ivo");
        author2.setLastName("Andrić");
        author2.setBiography("Nobelovac, pisac Na Drini ćuprija");
        authorService.saveAuthor(author2);

        Author author3 = new Author();
        author3.setFirstName("Mak");
        author3.setLastName("Dizdar");
        author3.setBiography("Bosanski pjesnik, autor Kamenog spavača");
        authorService.saveAuthor(author3);

        Author author4 = new Author();
        author4.setFirstName("Skender");
        author4.setLastName("Kulenović");
        author4.setBiography("Bosanski pjesnik i dramski pisac");
        authorService.saveAuthor(author4);

        Author author5 = new Author();
        author5.setFirstName("Isak");
        author5.setLastName("Kikić");
        author5.setBiography("Bosanski pisac i novinar");
        authorService.saveAuthor(author5);

        Category category1 = new Category();
        category1.setName("Roman");
        category1.setDescription("Prozno književno djelo većeg obima");
        categoryService.saveCategory(category1);

        Category category2 = new Category();
        category2.setName("Poezija");
        category2.setDescription("Književni rod koji koristi ritam i metaforu");
        categoryService.saveCategory(category2);

        Category category3 = new Category();
        category3.setName("Drama");
        category3.setDescription("Književni rod namijenjen izvođenju na pozornici");
        categoryService.saveCategory(category3);

        Category category4 = new Category();
        category4.setName("Povijest");
        category4.setDescription("Knjige koje obrađuju povijesne teme");
        categoryService.saveCategory(category4);

        Category category5 = new Category();
        category5.setName("Filozofija");
        category5.setDescription("Knjige o filozofskim pitanjima");
        categoryService.saveCategory(category5);

        Category category6 = new Category();
        category6.setName("Znanost");
        category6.setDescription("Knjige o znanstvenim otkrićima");
        categoryService.saveCategory(category6);

        Book book1 = new Book();
        book1.setTitle("Na Drini ćuprija");
        book1.setIsbn("9789530123456");
        book1.setPublishedDate(Date.from(LocalDate.of(1945, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        book1.setAvailableCopies(5);
        book1.setAuthor(author2);
        book1.setCategory(category1);
        bookService.saveBook(book1);

        Book book2 = new Book();
        book2.setTitle("Derviš i smrt");
        book2.setIsbn("9789530123467");
        book2.setPublishedDate(Date.from(LocalDate.of(1966, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        book2.setAvailableCopies(3);
        book2.setAuthor(author1);
        book2.setCategory(category1);
        bookService.saveBook(book2);

        Book book3 = new Book();
        book3.setTitle("Kameni spavač");
        book3.setIsbn("9789530123478");
        book3.setPublishedDate(Date.from(LocalDate.of(1966, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        book3.setAvailableCopies(4);
        book3.setAuthor(author3);
        book3.setCategory(category2);
        bookService.saveBook(book3);

        Book book4 = new Book();
        book4.setTitle("Stojanka majka Knežopoljka");
        book4.setIsbn("9789530123489");
        book4.setPublishedDate(Date.from(LocalDate.of(1945, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        book4.setAvailableCopies(2);
        book4.setAuthor(author4);
        book4.setCategory(category2);
        bookService.saveBook(book4);

        Book book5 = new Book();
        book5.setTitle("Mudraci");
        book5.setIsbn("9789530123490");
        book5.setPublishedDate(Date.from(LocalDate.of(1936, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        book5.setAvailableCopies(3);
        book5.setAuthor(author5);
        book5.setCategory(category1);
        bookService.saveBook(book5);

        System.out.println("Baza podataka je uspješno inicijalizovana sa početnim podacima!");
    }
}
