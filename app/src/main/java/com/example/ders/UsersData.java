package com.example.ders;


import java.util.ArrayList;

public class UsersData {

    private static final UsersData instance = new UsersData();

    ArrayList<User> users;

    private UsersData() {
        users = new ArrayList<>();
        User u = new User("ekrem", "12345");
        users.add(u);
        u = new User("faruk", "123456");
        users.add(u);
        u = new User("omer", "1234567");
        users.add(u);
    }

    public static UsersData getInstance(){
        return instance;
    }
}
