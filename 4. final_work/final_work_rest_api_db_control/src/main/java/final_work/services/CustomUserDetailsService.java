package final_work.services;

import final_work.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import final_work.models.User;

/**
 * Сервис безопасности
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserRepository dao;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User myUser= dao.findOneByLogin(userName);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: " + userName);
        }
        return org.springframework.security.core.userdetails.User.builder()
                    .username(myUser.getLogin())
                    .password(myUser.getPassword())
                    .roles(myUser.getRole())
                    .build();
    }
}
