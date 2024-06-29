package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import server.Socket.SocketResponseSender;
import shared.Model.User;
import shared.Request.*;
import shared.Response.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClientHandler extends Thread implements RequestHandler {

    private SocketResponseSender socketResponseSender;
    private DataBase dataBase;


    public ClientHandler(SocketResponseSender socketResponseSender, DataBase dataBase) {
        this.dataBase = dataBase;
        this.socketResponseSender = socketResponseSender;
    }



    @Override
    public void run() {
        try {

            while (true) {
                Response response = socketResponseSender.getRequest().run(this);
                socketResponseSender.sendResponse(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                socketResponseSender.close();
                dataBase.saveUsers(); // Save users when the server stops

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Response handleSignUpRequest(SignUpRequest signUpRequest) {
        User user = new User(signUpRequest.getUsername(),signUpRequest.getPassword());
        File newFolder = new File("C:\\Users\\shaya\\IdeaProjects\\AP-assignment5\\server\\src\\main\\java\\server\\data\\" + signUpRequest.getUsername());
        boolean created = newFolder.mkdirs();
        if (created) {
            System.out.println("Folder created successfully at: " + newFolder.getPath());
        } else {
            System.out.println("Failed to create the folder at: " + newFolder.getPath());
        }
        dataBase.getUsers().add(user);
        dataBase.saveUsers();
        return new SignUpResponse();
    }

    @Override
    public Response handleValidUsernameRequest(ValidUsernameRequest validUsernameRequest) {
        ValidUsernameResponse validUsernameResponse = new ValidUsernameResponse();
        for (User user : dataBase.getUsers()) {
            if (validUsernameRequest.getUsername().equals(user.getUsername())) {
                validUsernameResponse.valid = false;
                return validUsernameResponse;
            }
        }
        validUsernameResponse.valid = true;
        return  validUsernameResponse;
    }

    @Override
    public Response handleLoginRequest(LoginRequest loginRequest) {
        return new LoginResponse(loginRequest.getUsername());
    }

    @Override
    public Response handleUsernameExistRequest(UserNameExistRequest userNameExistRequest) {
        UserNameExistResponse userNameExistResponse = new UserNameExistResponse();
        for (User user : dataBase.getUsers()){
            if (userNameExistRequest.getUsername().equals(user.getUsername())){
                userNameExistResponse.exists = true;
                return userNameExistResponse;
            }
        }
        userNameExistResponse.exists = false;
        return userNameExistResponse;
    }

    @Override
    public Response handleCheckPasswordRequest(CheckPasswordRequest checkPasswordRequest) {
        CheckPasswordResponse checkPasswordResponse = new CheckPasswordResponse();
        for (User user:dataBase.getUsers()){
            if (checkPasswordRequest.getUsername().equals(user.getUsername())){
                if (checkPasswordRequest.getPassword().equals(user.getPassword())){
                     checkPasswordResponse.ableToLogin = true;
                     return checkPasswordResponse;
                }
            }
        }
        checkPasswordResponse.ableToLogin = false;
        return checkPasswordResponse;
    }

    @Override
    public Response handleSeeFilesRequest(SeeFilesRequest seeFilesRequest) {
        List<String> fileNames = dataBase.listFilesUsingFileClass("C:\\Users\\shaya\\IdeaProjects\\AP-assignment5\\server\\src\\main\\java\\server\\data\\"+seeFilesRequest.getUsername());
        SeeFilesResponse seeFilesResponse = new SeeFilesResponse(fileNames);
       return seeFilesResponse;
    }
}
