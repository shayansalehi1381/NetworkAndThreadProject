package server;

import server.Socket.SocketResponseSender;
import shared.Model.User;
import shared.Request.LoginRequest;
import shared.Request.RequestHandler;
import shared.Request.SignUpRequest;
import shared.Request.ValidUsernameRequest;
import shared.Response.*;

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
            socketResponseSender.close();
        }
    }

    @Override
    public Response handleSignUpRequest(SignUpRequest signUpRequest) {
        User user = new User(signUpRequest.getUsername(),signUpRequest.getPassword());
        dataBase.getUsers().add(user);
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
        return  validUsernameResponse;
    }

    @Override
    public Response handleLoginRequest(LoginRequest loginRequest) {
        System.out.println("recived");
        return new LoginResponse();
    }
}
