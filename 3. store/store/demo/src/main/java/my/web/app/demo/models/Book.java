package my.web.app.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title,author,descriptyion;
    private int price;

    public Book() {
    }

    public Book(String title, String author, String descriptyion, int price) {
        this.title = title;
        this.author = author;
        this.descriptyion = descriptyion;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getDescriptyion() {
        return descriptyion;
    }

    public void setDescriptyion(String descriptyion) {
        this.descriptyion = descriptyion;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
