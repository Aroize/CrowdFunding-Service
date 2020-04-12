package ru.ifmo.server.data.entities;

import javax.persistence.*;

@Entity()
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "users_id_seq")
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "hashed_pwd")
    private String hashedPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
