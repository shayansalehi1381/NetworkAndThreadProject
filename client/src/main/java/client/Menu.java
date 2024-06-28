package client;

import client.Socket.SocketRequestSender;
import shared.Request.LoginRequest;
import shared.Request.SignUpRequest;
import shared.Request.ValidUsernameRequest;
import shared.Response.*;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu implements ResponseHandler {

    public SocketRequestSender socketRequestSender;
    private boolean isUsernameValid = false;
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

    @Override
    public void handleSignUpResponse(SignUpResponse signUpResponse) {
        System.out.println("sdasdqw");
    }

    @Override
    public void handleLoginResponse(LoginResponse loginResponse) {
        System.out.println("asasasa");
    }

    @Override
    public void handleValidUsernameResponse(ValidUsernameResponse validUsernameResponse) {
        isUsernameValid = validUsernameResponse.valid;
    }
}
