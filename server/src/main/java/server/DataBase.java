package server;


import shared.Model.User;

import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private static List<User> users = new ArrayList<>();

    public List<User> getClients() {
        return users;
    }

    public void setClients(List<User> clients) {
        this.users = clients;
    }
}
