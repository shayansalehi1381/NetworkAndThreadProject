package client;

import client.Socket.SocketRequestSender;
import shared.Request.*;
import shared.Response.*;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu implements ResponseHandler {

    public SocketRequestSender socketRequestSender;
    private boolean isUsernameValid = false;
    private boolean isAbleToLogin = false;
    private boolean isUsernameExists = true;//this is for Login
    public Menu(SocketRequestSender socketRequestSender){
        this.socketRequestSender = socketRequestSender;
    }


    public void startMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome!");

        while (true){
            System.out.println("What do you want to do?");
            System.out.println("1.login");
            System.out.println("2.sign up");

            try {
                int input = scanner.nextInt();
                if (input == 1){
                    login(scanner);
                    break;
                }
                else if (input == 2){
                    signUp(scanner);
                    break;
                }
            }catch (InputMismatchException e){
                System.out.println("wrong input! try again.");
                System.out.println("***********************");
                scanner.next();
            }
        }
    }



    private void signUp(Scanner scanner){
        System.out.println("Choose your Username:");
        String username = scanner.next();
        if (ValidUsername(username)) {
            System.out.println("Choose your password:");
            String password = scanner.next();
            try {
                socketRequestSender.sendRequest(new SignUpRequest(username, password)).run(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Username already exists. Please choose another one.");
            signUp(scanner);
        }
    }

    private void login(Scanner scanner){
        System.out.println("Type your username:");
        String username = scanner.next();
        if (usernameExists(username)){
            System.out.println("Type your Password:");
            String password = scanner.next();
            if (checkPassword(username,password)){
                try {
                    socketRequestSender.sendRequest(new LoginRequest()).run(this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                System.out.println("Wrong PassWord! try again.");
                login(scanner);
            }
        }
        else {
            System.out.println("This Username does not Exist in the DataBase! try again.");
            login(scanner);
        }
    }

    private boolean ValidUsername(String username){
        try {
            ValidUsernameResponse response = (ValidUsernameResponse) socketRequestSender.sendRequest(new ValidUsernameRequest(username));
            response.run(this);
            return isUsernameValid;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkPassword(String username,String pass){
        try {
            socketRequestSender.sendRequest(new CheckPasswordRequest(username,pass)).run(this);
            return isAbleToLogin;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean usernameExists(String username){
        try {
            socketRequestSender.sendRequest(new UserNameExistRequest(username)).run(this);
            return isUsernameExists;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleSignUpResponse(SignUpResponse signUpResponse) {
        System.out.println("**************************************************");
        System.out.println("Your Account is Created!");
        startMenu();
    }

    @Override
    public void handleLoginResponse(LoginResponse loginResponse) {
        System.out.println("**************************************************");
        System.out.println("login Successful!");
    }

    @Override
    public void handleValidUsernameResponse(ValidUsernameResponse validUsernameResponse) {
        isUsernameValid = validUsernameResponse.valid;
    }

    @Override
    public void handleUsernameExistResponse(UserNameExistResponse userNameExistResponse) {
        isUsernameExists = userNameExistResponse.exists;
    }

    @Override
    public void handleCheckPasswordResponse(CheckPasswordResponse checkPasswordResponse) {
        isAbleToLogin = checkPasswordResponse.ableToLogin;
    }
}
