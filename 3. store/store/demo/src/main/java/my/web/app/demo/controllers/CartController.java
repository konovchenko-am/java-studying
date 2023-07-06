package my.web.app.demo.controllers;

import my.web.app.demo.models.Book;
import my.web.app.demo.models.CartProduct;
import my.web.app.demo.repo.BookRepository;
import my.web.app.demo.repo.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") long bookId){
        CartProduct cartProduct = cartRepository.findOneByBookId(bookId).orElse(null);
       if(cartProduct != null){//товар в корзине найден
           cartProduct.setCount(cartProduct.getCount() + 1);//к текущему количеству товаров прибавили 1
       }else {
           var book = bookRepository.findById(bookId).orElse(null);
           if(book != null){
               cartProduct = new CartProduct(book,book.getPrice(),1);
           }else{
               return "redirect:/";
           }
       }
        cartRepository.save(cartProduct);
        return "redirect:/";
    }

    @GetMapping("")
    public String main(Model model){
        Iterable<CartProduct> goods = cartRepository.findAllByOrderByBookIdAsc(); //получили все товары корзины
        model.addAttribute("content","blocks/cart");
        model.addAttribute("goods",goods);
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id){
        cartRepository.deleteById(id);
        return "redirect:/cart";
    }

}
