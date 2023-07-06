package my.web.app.demo.repo;

import my.web.app.demo.models.Book;
import my.web.app.demo.models.CartProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<CartProduct,Long> {
//    @Query("select * from ...,nativeQuery = true)
//    CartProduct getMyCartProduct(long id){
//
//    }
    Iterable<CartProduct> findAllByOrderByBookIdAsc();
    public Optional<CartProduct> findOneByBookId(long bookId);
}
