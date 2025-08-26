package org.example.subwp.controller;

import org.example.subwp.model.Author;
import org.example.subwp.model.Book;
import org.example.subwp.model.Category;
import org.example.subwp.model.Loan;
import org.example.subwp.model.Role;
import org.example.subwp.model.User;
import org.example.subwp.service.AuthorService;
import org.example.subwp.service.BookService;
import org.example.subwp.service.CategoryService;
import org.example.subwp.service.LoanService;
import org.example.subwp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping
    public String listLoans(Model model, Authentication authentication) {
        try {

            if (authentication == null) {
                System.out.println("Authentication object is null");
                return "redirect:/login";
            }

            String currentUsername = authentication.getName();
            System.out.println("Logged-in username: " + currentUsername);
            User currentUser = userService.getUserByUsername(currentUsername).orElse(null);
            if (currentUser == null) {
                System.out.println("No user found with username: " + currentUsername);
                return "redirect:/login";
            }

            boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            System.out.println("Is Admin: " + isAdmin);
            List<Loan> loans;

            if (isAdmin) {
                loans = loanService.getAllLoans();
            } else {
                loans = loanService.getLoansByUserId(currentUser.getId());
            }
            model.addAttribute("loans", loans);
            return "loans/list";
        } catch (Exception e) {
            System.err.println("Error fetching loans: " + e.getMessage());
            e.printStackTrace();
            return "error/500";
        }
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Loan loan = new Loan();
        loan.setLoanDate(new Date());
        model.addAttribute("loan", loan);
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("users", userService.getAllUsers());
        return "loans/create";
    }


    @PostMapping
    public String createLoan(@Valid @ModelAttribute("loan") Loan loan, 
                           @RequestParam("bookTitle") String bookTitle,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("books", bookService.getAvailableBooks());
            return "loans/create";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username).orElse(null);

        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "loans/create";
        }
        Book book = bookService.findByTitle(bookTitle);
        if (book == null) {
            model.addAttribute("error", "Odabrana knjiga ne postoji. Molimo odaberite knjigu iz liste.");
            model.addAttribute("books", bookService.getAllBooks());
            return "loans/create";
        }
        
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(new Date());
        loanService.saveLoan(loan);
        return "redirect:/loans";
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Loan loan = loanService.getLoanById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid loan Id:" + id));
        model.addAttribute("loan", loan);
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("users", userService.getAllUsers());
        return "loans/edit";
    }

    @PostMapping("/{id}")
    public String updateLoan(@PathVariable Long id, @Valid @ModelAttribute("loan") Loan loan,
                           @RequestParam("bookTitle") String bookTitle,
                           @RequestParam("username") String username,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("users", userService.getAllUsers());
            return "loans/edit";
        }
        
        Book book = bookService.findByTitle(bookTitle);
        if (book == null) {
            model.addAttribute("error", "Odabrana knjiga ne postoji. Molimo odaberite knjigu iz liste.");
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("users", userService.getAllUsers());
            return "loans/edit";
        }
        
        // Kreiraj ili pronađi korisnika
        User user = userService.getUserByUsername(username).orElse(null);
        if (user == null) {
            // Kreiraj novog korisnika s default podacima
            user = new User();
            user.setUsername(username);
            user.setPassword("defaultPassword"); // Možete dodati logiku za generiranje lozinke
            user.setEmail(username + "@example.com");
            user.setRole(Role.USER);
            user.setFirstName(username);
            user.setLastName("Default");
            user = userService.registerUser(user).orElse(null);
        }
        
        loan.setBook(book);
        loan.setUser(user);
        loan.setId(id);
        loanService.saveLoan(loan);
        return "redirect:/loans";
    }

    @GetMapping("/{id}/delete")
    public String deleteLoan(@PathVariable Long id) {
        loanService.deleteLoanById(id);
        return "redirect:/loans";
    }
}