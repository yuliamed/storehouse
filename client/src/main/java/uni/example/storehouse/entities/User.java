package uni.example.storehouse.entities;

import java.io.Serializable;


public class User implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String login;

    private String pass = "1243";

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

    private String name;
    private String surname;

    private String patronymic;
    private Byte role;
    private Storehouse storehouse;

    public Integer getIntIdShouse() {
        if(storehouse!=null)return this.storehouse.getId();
        else return -1;
    }

    public void setIntIdShouse(Integer intIdShouse) {
        this.intIdShouse = storehouse.getId();
    }

    private Integer intIdShouse=-1;


    public User() {

    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPass() {
        return this.pass;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getPatronymic() {
        return this.patronymic;
    }

    public Byte getRole() {
        return this.role;
    }

    public Storehouse getIdStorehouse() {
        return this.storehouse;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
