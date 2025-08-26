package org.example.subwp.controller;

import org.example.subwp.model.Author;
import org.example.subwp.model.Book;
import org.example.subwp.model.Category;
import org.example.subwp.service.AuthorService;
import org.example.subwp.service.BookService;
import org.example.subwp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listBooks(@RequestParam(value = "title", required = false) String title, Model model) {
        List<Book> books;


        if (title != null && !title.isEmpty()) {
            books = bookService.findBooksByTitle(title);
        } else {
            books = bookService.getAllBooks();
        }

        model.addAttribute("books", books);
        model.addAttribute("searchQuery", title);
        return "books/list";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        List<Author> authors = authorService.getAllAuthors();
        List<Category> categories = categoryService.getAllCategories();
        
        if (authors.isEmpty()) {
            model.addAttribute("error", "Prvo trebate dodati autore prije nego što možete kreirati knjigu.");
            return "books/create";
        }
        
        if (categories.isEmpty()) {
            model.addAttribute("error", "Prvo trebate dodati kategorije prije nego što možete kreirati knjigu.");
            return "books/create";
        }
        
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);
        return "books/create";
    }

    @PostMapping("/new")
    public String createBook(@RequestParam("title") String title,
                           @RequestParam("isbn") String isbn,
                           @RequestParam(value = "publishedDate", required = false) String publishedDate,
                           @RequestParam("availableCopies") Integer availableCopies,
                           @RequestParam("authorId") Long authorId,
                           @RequestParam("categoryId") Long categoryId,
                           Model model) {
        System.out.println("Creating book with ISBN: " + isbn);
        if (title == null || title.trim().isEmpty()) {
            model.addAttribute("error", "Naslov je obavezan");
            return "books/create";
        }

        if (isbn == null || isbn.trim().isEmpty()) {
            model.addAttribute("error", "ISBN je obavezan");
            return "books/create";
        }

        if (availableCopies == null || availableCopies < 0) {
            model.addAttribute("error", "Broj dostupnih primjeraka mora biti 0 ili veći");
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "books/create";
        }

        if (authorId == null) {
            model.addAttribute("error", "Morate odabrati autora");
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "books/create";
        }

        if (categoryId == null) {
            model.addAttribute("error", "Morate odabrati kategoriju");
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "books/create";
        }

        Book existingBook = bookService.findByIsbn(isbn);
        if (existingBook != null) {
            model.addAttribute("error", "ISBN već postoji!");
            return "books/create";
        }
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAvailableCopies(availableCopies);
        if (publishedDate != null && !publishedDate.trim().isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                book.setPublishedDate(dateFormat.parse(publishedDate));
            } catch (Exception e) {
                book.setPublishedDate(null);
            }
        }
        Author author = authorService.getAuthorById(authorId).orElse(null);
        if (author == null) {
            model.addAttribute("error", "Odabrani autor ne postoji");
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "books/create";
        }
        book.setAuthor(author);
        Category category = categoryService.getCategoryById(categoryId).orElse(null);
        if (category == null) {
            model.addAttribute("error", "Odabrana kategorija ne postoji");
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "books/create";
        }
        book.setCategory(category);

        try {
            bookService.saveBook(book);
            return "redirect:/books";
        } catch (Exception e) {
            model.addAttribute("error", "Greška pri spremanju knjige: " + e.getMessage());
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "books/create";
        }
    }



    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "books/edit";
    }


    @PostMapping("/{id}")
    public String updateBook(@PathVariable Long id,
                             @Valid @ModelAttribute("book") Book book,
                             @RequestParam("authorId") Long authorId,
                             @RequestParam("categoryId") Long categoryId,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "books/edit";
        }

        Book existingBook = bookService.findByIsbn(book.getIsbn());
        if (existingBook != null && !existingBook.getId().equals(id)) {
            result.rejectValue("isbn", "error.book", "ISBN već postoji!");
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "books/edit";
        }

        // Pronađi autora
        Author author = authorService.getAuthorById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author Id"));
        book.setAuthor(author);

        // Pronađi kategoriju
        Category category = categoryService.getCategoryById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id"));
        book.setCategory(category);

        bookService.saveBook(book);
        return "redirect:/books";
    }


    @GetMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBookById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Knjiga je uspješno obrisana.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ne možete obrisati ovu knjigu jer postoje posuđene kopije povezane s njom.");
        }
        return "redirect:/books";
    }

    @GetMapping("/books/search")
    public String searchBooks(@RequestParam("title") String title, Model model) {
        List<Book> books = bookService.findBooksByTitle(title);
        model.addAttribute("books", books);
        return "books";
    }



}

