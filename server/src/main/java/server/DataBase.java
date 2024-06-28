package server;


import com.fasterxml.jackson.databind.ObjectMapper;
import shared.Model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private static final String FILE_PATH = "C:\\Users\\shaya\\IdeaProjects\\AP-assignment5\\users.json";
    private ObjectMapper objectMapper;
    private List<User> users = new ArrayList();



    public DataBase() {
        this.objectMapper = ServerConfig.getObjectMapper(); // Use configured ObjectMapper
        loadUsers();
    }


    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void saveUsers() {

        try {
            objectMapper.writeValue(new File(FILE_PATH), users);
            System.out.println("Users saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save users to file.");
        }
    }


    public void loadUsers() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try {
                users = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
                System.out.println("Users loaded successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to load users from file.");
            }
        } else {
            System.out.println("Users file does not exist.");
        }
    }


}
