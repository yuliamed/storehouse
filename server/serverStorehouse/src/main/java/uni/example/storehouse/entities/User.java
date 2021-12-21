package uni.example.storehouse.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    @Id
    @Column(name = "login")
    private String login;
    @Column(name = "pass")
    private String pass = "1243";
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "role")
    private Byte role;


    @ManyToOne()
    @JoinColumn(name="idStorehouse")
    private Storehouse storehouse;

    public Storehouse getStorehouse(){
        return this.storehouse;
    }


    public User() {

    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setRole(Byte role) {
        this.role = role;
    }

    public void setStorehouse(Storehouse storehouse) {
        this.storehouse = storehouse;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Byte getRole() {
        return role;
    }

    public void setPost(Storehouse storehouse) {
    }
}
