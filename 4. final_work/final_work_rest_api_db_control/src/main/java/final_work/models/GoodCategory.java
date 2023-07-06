package final_work.models;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * Класс представления категорий товаров
 */
@Entity
@Table(name="categories")
@Transactional
public class GoodCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String title;

    public GoodCategory() {
    }

    public GoodCategory(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
