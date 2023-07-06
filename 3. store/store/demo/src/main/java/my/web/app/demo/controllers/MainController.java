package my.web.app.demo.controllers;

import my.web.app.demo.models.Book;
import my.web.app.demo.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String main(Model model){
        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("content","blocks/main");
        model.addAttribute("books",books);
        return "index";
    }
    @GetMapping("/book/add")
    public String addForm(Model model){
        model.addAttribute("content","blocks/add");
        return "index";
    }

    @PostMapping("/book/add")
    public String addBook(@RequestParam String title,
                          @RequestParam String author,
                          @RequestParam String descriptyion,
                          @RequestParam int price
                          ){
        var book = new Book(title,author,descriptyion,price);
        bookRepository.save(book);//вставили товар в базу
        return "redirect:/";
    }

    @GetMapping("/book/{id}")
    public String showCard(@PathVariable(value = "id") long id, Model model){
        var book = bookRepository.findById(id).get();
        model.addAttribute("content","blocks/card");
        model.addAttribute("book",book);
        return "index";
    }

    @GetMapping("/book/edit/{id}")
    public String formUpdateBook(@PathVariable(value = "id") long id, Model model){
        var book = bookRepository.findById(id).get();
        model.addAttribute("book",book);
        model.addAttribute("content","blocks/edit");
        return "index";
    }

    @PostMapping("/book/edit/{id}")
    public String editBook(@PathVariable(value = "id") long id,
                           @RequestParam String title,
                           @RequestParam String author,
                           @RequestParam String descriptyion,
                           @RequestParam int price
                           ){
        var book = bookRepository.findById(id).get();
        book.setAuthor(author);
        book.setTitle(title);
        book.setDescriptyion(descriptyion);
        book.setPrice(price);
        bookRepository.save(book);//зафиксировали все изменения
        return "redirect:/";
    }
}
