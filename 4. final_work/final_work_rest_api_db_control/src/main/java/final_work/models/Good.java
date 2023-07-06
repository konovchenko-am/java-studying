package final_work.models;

import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Null;

/**
 * Класс товара - представление для обозначения сущности товара, содержит наименование, информацию и цену
 * (Классом представления физического товара на складе с указанием количества является класс StockGood)
 */
@Entity
@Table(name="goods")
@Transactional
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String title;      // наименование товара

    private String info;       // описание товара

    private int price;      // цена товара


    @ManyToOne(fetch = FetchType.EAGER)
    @Nullable
    private GoodCategory category;

    public Good() {
    }

    public Good(String title, String info, int price, GoodCategory category) {
        this.title = title;
        this.info = info;
        this.price = price;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public int getPrice() {
        return price;
    }

    public GoodCategory getCategory() {
        return category;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCategory(GoodCategory goodCategory) {
        this.category = goodCategory;
    }

}
