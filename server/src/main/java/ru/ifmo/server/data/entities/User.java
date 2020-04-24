package ru.ifmo.server.data.entities;

import javax.persistence.*;

@Entity()
@Table(name = "users", schema = "crowdfunding")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "users_id_seq")
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "hashed_pwd")
    private String hashedPassword;

    private int balance;

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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int bill) {
        this.balance = bill;
    }

    public void addAmount(int amount) { this.balance += amount; }
}
