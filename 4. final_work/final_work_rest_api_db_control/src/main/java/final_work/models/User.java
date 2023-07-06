package final_work.models;


import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * Класс представления пользователя
 */
@Entity
@Table(name="users")
@Transactional
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    private String login;
    private String password;
    private String role;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
