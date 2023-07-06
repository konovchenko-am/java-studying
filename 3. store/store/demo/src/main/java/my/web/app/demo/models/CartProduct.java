package my.web.app.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name="cart")
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id",unique = true,nullable = false,updatable = false)
    private Book book;
    private int price, count;

    public CartProduct() {
    }

    public CartProduct(Book book, int price, int count) {
        this.book = book;
        this.price = price;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
