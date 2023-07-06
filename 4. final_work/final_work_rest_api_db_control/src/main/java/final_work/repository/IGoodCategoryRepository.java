package final_work.repository;

import final_work.models.GoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGoodCategoryRepository extends JpaRepository<GoodCategory, Long> {
}
