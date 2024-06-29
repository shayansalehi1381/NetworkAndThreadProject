package server;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import shared.Model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private static final String FILE_PATH = "users.json";
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
        try {
            TypeReference<List<User>> typeReference = new TypeReference<>() {
            };
            users = objectMapper.readValue(file, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByUsername(String username){
        for (User user:users){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public void addFileToUser(String username, String fileName) {
        User user = getUserByUsername(username);
        if (user != null) {
            user.getFiles().add(new File(fileName));
        }
    }
}
