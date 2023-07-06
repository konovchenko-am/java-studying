package final_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import final_work.models.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findOneByLogin(String login);

    boolean existsByLogin(String login);
}
