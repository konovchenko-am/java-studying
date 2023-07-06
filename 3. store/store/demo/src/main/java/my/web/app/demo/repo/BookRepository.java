package my.web.app.demo.repo;

import my.web.app.demo.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book,Long> {

}
