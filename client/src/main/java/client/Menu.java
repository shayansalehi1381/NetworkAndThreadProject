package client;

import client.Socket.SocketRequestSender;
import shared.Request.ValidUsernameRequest;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public SocketRequestSender socketRequestSender;
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
        ValidUsername(username);
        System.out.println("Choose your password:");
        String password = scanner.next();

    }

    private void login(Scanner scanner){
        System.out.println("Type your username:");
        String username = scanner.next();
    }

    private void ValidUsername(String userName){
        try {
            socketRequestSender.sendRequest(new ValidUsernameRequest(userName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
