package org.example.application_entity.Client;


public class Client {

    private String id;

    private String nick_name;

    private String username;

    private String password;

    private Balance balance = new Balance();

    public Client(String id, String nick_name, String username, String password) {
        this.id = id;
        this.nick_name = nick_name;
        this.username = username;
        this.password = password;
    }


    public String getId() {
        return id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Balance getBalance() {
        return balance;
    }


}
