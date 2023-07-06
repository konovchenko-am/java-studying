package final_work.services;

import final_work.models.User;
import final_work.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с пользователями
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public Iterable<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public User read(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User update(User user, long id) {
        if(userRepository.existsById(id)){
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
